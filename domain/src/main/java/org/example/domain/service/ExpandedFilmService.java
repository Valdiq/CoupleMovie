package org.example.domain.service;

import io.r2dbc.spi.R2dbcDataIntegrityViolationException;
import org.example.domain.document.GenreEmotionDocument;
import org.example.domain.entity.ExpandedFilmEntity;
import org.example.domain.exception.FilmSaveException;
import org.example.domain.mapper.FilmMapper;
import org.example.domain.model.ExpandedFilmDTO;
import org.example.domain.properties.OMDBProperties;
import org.example.domain.repository.ExpandedFilmRepository;
import org.example.domain.repository.GenreEmotionRepository;
import org.example.logging.logger.ServiceLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ExpandedFilmService {

    private static final Logger log = LoggerFactory.getLogger(ExpandedFilmService.class);


    private final ExpandedFilmRepository expandedFilmRepository;

    private final OMDBProperties properties;

    private final WebClient webClient;

    private final List<ServiceLogger> loggerList;

    private final FilmMapper filmMapper;

    private final GenreEmotionRepository genreEmotionRepository;

    private final AWSS3Service awss3Service;

    public ExpandedFilmService(ExpandedFilmRepository expandedFilmRepository, OMDBProperties properties, WebClient.Builder webClient, List<ServiceLogger> loggerList, FilmMapper filmMapper, GenreEmotionRepository genreEmotionRepository, AWSS3Service awss3Service) {
        this.expandedFilmRepository = expandedFilmRepository;
        this.properties = properties;
        this.loggerList = loggerList;
        this.filmMapper = filmMapper;
        this.genreEmotionRepository = genreEmotionRepository;
        this.awss3Service = awss3Service;
        this.webClient = webClient.baseUrl(properties.baseUrl()).build();
    }

    @Transactional(rollbackFor = Exception.class)
    public Mono<Void> saveFilmToDatabase(Mono<ExpandedFilmDTO> expandedFilmDTOMono) {
        return expandedFilmDTOMono
                .flatMap(expandedFilmDTO -> Mono.just(awss3Service.putObjectInBucket(expandedFilmDTO.imdbId(), expandedFilmDTO.poster()))
                        .flatMap(s3Url -> Mono.just(filmMapper.expandedFilmDTOToEntity(expandedFilmDTO).setPoster(s3Url)))
                )
                .flatMap(expandedFilmRepository::save)
                .doOnError(e -> {
                    loggerList.forEach(logger ->
                            logger.log(this.getClass().getName(),
                                    "saveFilmToDatabase",
                                    e.getClass().toString(),
                                    "Error saving expanded film - " + e.getMessage(),
                                    LocalDateTime.now())
                    );
                })
                .onErrorResume(R2dbcDataIntegrityViolationException.class, e -> {
                    log.warn("Data integrity violation while saving expanded film: {}", e.getMessage());
                    return Mono.empty();
                })
                .onErrorMap(e -> new FilmSaveException("Error saving expanded film - " + e.getMessage()))
                .then();
    }

    @Cacheable(value = "expanded_film")
    public Mono<ExpandedFilmDTO> getFilm(String imdbId) {
        var response = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("i", imdbId)
                        .queryParam("plot", "long")
                        .build())
                .retrieve()
                .bodyToMono(ExpandedFilmDTO.class);

        saveFilmToDatabase(response).subscribe(
                unused -> log.debug("Successfully saved expanded film for imdbId: {}", imdbId),
                error -> log.error("Error saving expanded film for imdbId: {}: {}", imdbId, error.getMessage())
        );

        return response;
    }

    public Mono<ExpandedFilmDTO> getRandomFilm() {
        ExpandedFilmEntity randomFilm = expandedFilmRepository.count()
                .flatMap(count -> {
                    int randomOffset = (int) (Math.random() * count);
                    return expandedFilmRepository.findRandomFilm(randomOffset);
                }).block();

        return Mono.just(filmMapper.expandedFilmEntityToDTO(randomFilm));
    }

    public Flux<ExpandedFilmDTO> getFilmsByEmotions(List<String> emotions) {
        List<GenreEmotionDocument> documents = genreEmotionRepository.findByEmotionsIn(emotions);

        return expandedFilmRepository.findAll()
                .filter(expandedFilm -> {
                    String[] filmGenres = expandedFilm.getGenre().split(", ");
                    return documents.stream()
                            .anyMatch(document -> {
                                for (String filmGenre : filmGenres) {
                                    if (document.getGenre().equalsIgnoreCase(filmGenre)) {
                                        return true;
                                    }
                                }
                                return false;
                            });
                })
                .map(filmMapper::expandedFilmEntityToDTO);
    }

}
