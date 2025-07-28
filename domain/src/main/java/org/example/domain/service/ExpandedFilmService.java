package org.example.domain.service;

import org.example.domain.document.GenreEmotionDocument;
import org.example.domain.entity.ExpandedFilmEntity;
import org.example.domain.exception.FilmSaveException;
import org.example.domain.mapper.FilmMapper;
import org.example.domain.model.ExpandedFilmDTO;
import org.example.domain.properties.OMDBProperties;
import org.example.domain.repository.ExpandedFilmRepository;
import org.example.domain.repository.mongo.GenreEmotionRepository;
import org.example.logging.logger.ServiceLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ExpandedFilmService {

    private static final Logger log = LoggerFactory.getLogger(ExpandedFilmService.class);

    private final ExpandedFilmRepository expandedFilmRepository;

    private final OMDBProperties properties;

    private final RestTemplate restTemplate;

    private final List<ServiceLogger> loggerList;

    private final FilmMapper filmMapper;

    private final GenreEmotionRepository genreEmotionRepository;

    private final AWSS3Service awss3Service;

    public ExpandedFilmService(ExpandedFilmRepository expandedFilmRepository, OMDBProperties properties, RestTemplateBuilder restTemplateBuilder, List<ServiceLogger> loggerList, FilmMapper filmMapper, GenreEmotionRepository genreEmotionRepository, AWSS3Service awss3Service) {
        this.expandedFilmRepository = expandedFilmRepository;
        this.properties = properties;
        this.loggerList = loggerList;
        this.filmMapper = filmMapper;
        this.genreEmotionRepository = genreEmotionRepository;
        this.awss3Service = awss3Service;
        this.restTemplate = restTemplateBuilder.build();
    }

    @Transactional(rollbackFor = Exception.class)
    public void saveFilmToDatabase(ExpandedFilmDTO expandedFilmDTO) {

        try {
            String s3Url = awss3Service.putObjectInBucket(expandedFilmDTO.imdbId(), expandedFilmDTO.poster());

            ExpandedFilmEntity expandedFilmEntity = filmMapper.expandedFilmDTOToEntity(expandedFilmDTO).setPoster(s3Url);

            expandedFilmRepository.save(expandedFilmEntity);
        } catch (DataIntegrityViolationException e) {
            loggerList.forEach(logger ->
                    logger.log(this.getClass().getName(),
                            "saveFilmToDatabase",
                            e.getClass().toString(),
                            "Data integrity violation while saving expanded film - " + e.getMessage(),
                            LocalDateTime.now())
            );
        } catch (Exception e) {
            loggerList.forEach(logger ->
                    logger.log(this.getClass().getName(),
                            "saveFilmToDatabase",
                            e.getClass().toString(),
                            "Unexpected error while saving expanded film - " + e.getMessage(),
                            LocalDateTime.now())
            );
            throw new FilmSaveException("Error saving expanded film - " + e.getMessage());
        }
    }

    @Cacheable(value = "expanded_film")
    public ExpandedFilmDTO getFilm(String imdbId) {
        try {
            String uri = UriComponentsBuilder.fromHttpUrl(properties.baseUrl())
                    .queryParam("i", imdbId)
                    .queryParam("plot", "long")
                    .toUriString();

            ExpandedFilmDTO response = restTemplate.getForObject(uri, ExpandedFilmDTO.class);

            if (response != null) {
                try {
                    saveFilmToDatabase(response);
                    log.debug("Successfully saved expanded film for imdbId: {}", imdbId);
                } catch (Exception e) {
                    log.error("Error saving expanded film for imdbId: {}: {}", imdbId, e.getMessage());
                    throw new FilmSaveException("Error saving expanded film - " + e.getMessage());
                }
            }

            return response;

        } catch (RestClientException e) {
            loggerList.forEach(logger ->
                    logger.log(this.getClass().getName(),
                            "getFilm",
                            e.getClass().toString(),
                            "Error fetching expanded film - " + e.getMessage(),
                            LocalDateTime.now())
            );
            throw new FilmSaveException("Error fetching expanded film - " + e.getMessage());
        }
    }

    public ExpandedFilmDTO getRandomFilm() {
        long count = expandedFilmRepository.count();

        if (count == 0) {
            throw new NoSuchElementException("No films in the database.");
        }

        int randomOffset = (int) (Math.random() * count);

        Optional<ExpandedFilmEntity> randomFilm = expandedFilmRepository.findRandomFilm(randomOffset);

        return filmMapper.expandedFilmEntityToDTO(randomFilm
                .orElseThrow(() -> new NoSuchElementException("No film found at the random offset: " + randomOffset)));
    }


    public List<ExpandedFilmDTO> getFilmsByEmotions(List<String> emotions) {
        List<GenreEmotionDocument> documents = genreEmotionRepository.findByEmotionsIn(emotions);
        List<ExpandedFilmEntity> allFilms = expandedFilmRepository.findAll();

        Set<String> targetGenres = documents.stream()
                .map(GenreEmotionDocument::getGenre)
                .map(String::toLowerCase)
                .collect(Collectors.toSet());

        return allFilms.stream()
                .filter(film -> {
                    String[] filmGenres = film.getGenre().split(", ");
                    for (String genre : filmGenres) {
                        if (targetGenres.contains(genre.toLowerCase())) {
                            return true;
                        }
                    }
                    return false;
                })
                .map(filmMapper::expandedFilmEntityToDTO)
                .toList();
    }

}
