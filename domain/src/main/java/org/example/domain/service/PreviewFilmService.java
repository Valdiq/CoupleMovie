package org.example.domain.service;

import org.example.domain.entity.PreviewFilmEntity;
import org.example.domain.exception.FilmSaveException;
import org.example.domain.mapper.FilmMapper;
import org.example.domain.model.PreviewFilmDTO;
import org.example.domain.model.PreviewFilmResponse;
import org.example.domain.properties.OMDBProperties;
import org.example.domain.repository.PreviewFilmRepository;
import org.example.logging.logger.ServiceLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class PreviewFilmService {

    private static final Logger log = LoggerFactory.getLogger(PreviewFilmService.class);

    private final PreviewFilmRepository previewFilmRepository;

    private final OMDBProperties properties;

    private final List<ServiceLogger> loggerList;

    private final RestTemplate restTemplate;

    private final FilmMapper filmMapper;

    private final ExpandedFilmService expandedFilmService;

    private final AWSS3Service awss3Service;

    public PreviewFilmService(PreviewFilmRepository previewFilmRepository, OMDBProperties properties, RestTemplateBuilder restTemplateBuilder, List<ServiceLogger> loggerList, FilmMapper filmMapper, ExpandedFilmService expandedFilmService, AWSS3Service awss3Service) {
        this.previewFilmRepository = previewFilmRepository;
        this.properties = properties;
        this.loggerList = loggerList;
        this.filmMapper = filmMapper;
        this.expandedFilmService = expandedFilmService;
        this.awss3Service = awss3Service;
        this.restTemplate = restTemplateBuilder.build();
    }

    @Transactional(rollbackFor = Exception.class)
    public void saveFilmsToDatabase(List<PreviewFilmResponse> filmResponses) {
        for (PreviewFilmResponse filmResponse : filmResponses) {
            for (PreviewFilmDTO filmPreview : filmResponse.search()) {
                try {
                    String url = awss3Service.putObjectInBucket(filmPreview.imdbId(), filmPreview.poster());

                    PreviewFilmEntity film = filmMapper.previewFilmDTOToEntity(filmPreview);
                    film.setPoster(url);

                    PreviewFilmEntity savedPreviewFilm = previewFilmRepository.save(film);

                    expandedFilmService.getFilm(savedPreviewFilm.getImdbId());

                    log.debug("Saved film: {}", savedPreviewFilm.getImdbId());

                } catch (DataIntegrityViolationException e) {
                    loggerList.forEach(logger ->
                            logger.log(this.getClass().getName(),
                                    "saveFilmsToDatabase",
                                    e.getClass().toString(),
                                    "Data integrity violation while saving films - " + e.getMessage(),
                                    LocalDateTime.now())
                    );
                } catch (Exception e) {
                    loggerList.forEach(logger ->
                            logger.log(this.getClass().getName(),
                                    "saveFilmsToDatabase",
                                    e.getClass().toString(),
                                    "Unexpected error while saving films - " + e.getMessage(),
                                    LocalDateTime.now())
                    );
                    throw new FilmSaveException("Error saving films to database - " + e.getMessage());
                }
            }
        }
    }


    @Cacheable(value = "preview_film")
    public List<PreviewFilmResponse> searchFilms(String title, String type, String year, Integer page) {
        String uri = UriComponentsBuilder.fromHttpUrl(properties.baseUrl())
                .queryParam("s", title)
                .queryParamIfPresent("type", Optional.ofNullable(type))
                .queryParamIfPresent("y", Optional.ofNullable(year))
                .queryParamIfPresent("page", Optional.ofNullable(page))
                .toUriString();

        try {
            PreviewFilmResponse responseEntity = restTemplate.getForObject(uri, PreviewFilmResponse.class);
            List<PreviewFilmResponse> response = List.of(responseEntity);

            saveFilmsToDatabase(response);

            return response;
        } catch (RestClientException e) {
            loggerList.forEach(logger ->
                    logger.log(this.getClass().getName(),
                            "searchFilms",
                            e.getClass().toString(),
                            "Error searching films - " + e.getMessage(),
                            LocalDateTime.now())
            );
            throw new FilmSaveException("Error searching films - " + e.getMessage());
        }


    }

    @Cacheable("film_by_emotions")
    @Transactional(readOnly = true)
    public List<PreviewFilmDTO> searchFilmsByEmotions(List<String> emotions) {
        return expandedFilmService.getFilmsByEmotions(emotions)
                .stream().map(filmMapper::expandedFilmDTOToPreviewDTO).toList();
    }

}
