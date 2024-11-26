package org.example.domain.service;

import io.r2dbc.spi.R2dbcDataIntegrityViolationException;
import org.example.domain.exception.FilmSaveException;
import org.example.domain.mapper.FilmMapper;
import org.example.domain.model.PreviewFilmResponse;
import org.example.domain.properties.OMDBProperties;
import org.example.domain.repository.PreviewFilmRepository;
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
import java.util.Optional;

@Service
public class PreviewFilmService {

    private static final Logger log = LoggerFactory.getLogger(PreviewFilmService.class);

    private final PreviewFilmRepository previewFilmRepository;

    private final OMDBProperties properties;

    private final WebClient webClient;

    private final List<ServiceLogger> loggerList;

    private final FilmMapper filmMapper;

    private final ExpandedFilmService expandedFilmService;

    public PreviewFilmService(PreviewFilmRepository previewFilmRepository, OMDBProperties properties, WebClient.Builder webClient, List<ServiceLogger> loggerList, FilmMapper filmMapper, ExpandedFilmService expandedFilmService) {
        this.previewFilmRepository = previewFilmRepository;
        this.properties = properties;
        this.loggerList = loggerList;
        this.filmMapper = filmMapper;
        this.expandedFilmService = expandedFilmService;
        this.webClient = webClient.baseUrl(properties.baseUrl()).build();
    }

    @Transactional(rollbackFor = Exception.class)
    public void saveFilmsToDatabase(Flux<PreviewFilmResponse> filmResponses) {
        filmResponses.flatMap(filmResponse -> Flux.fromIterable(filmResponse.search()))
                .map(filmMapper::previewFilmDTOToEntity)
                .flatMap(film -> previewFilmRepository.save(film)
                        .flatMap(savedPreviewFilm -> expandedFilmService.getFilm(savedPreviewFilm.getImdbId())
                                .thenReturn(savedPreviewFilm))
                        .doOnError(e -> {
                            loggerList.forEach(logger ->
                                    logger.log(this.getClass().getName(),
                                            "saveFilmsToDatabase",
                                            e.getClass().toString(),
                                            "Error saving preview film to database - " + e.getMessage(),
                                            LocalDateTime.now())
                            );
                        })
                )
                .onErrorResume(R2dbcDataIntegrityViolationException.class, e -> {
                    loggerList.forEach(logger ->
                            logger.log(this.getClass().getName(),
                                    "saveFilmsToDatabase",
                                    e.getClass().toString(),
                                    "Data integrity violation while saving films - " + e.getMessage(),
                                    LocalDateTime.now())
                    );
                    return Mono.empty();
                })
                .onErrorMap(e -> {
                    loggerList.forEach(logger ->
                            logger.log(this.getClass().getName(),
                                    "saveFilmsToDatabase",
                                    e.getClass().toString(),
                                    "Unexpected error while saving films - " + e.getMessage(),
                                    LocalDateTime.now())
                    );
                    return new FilmSaveException("Error saving films to database - " + e.getMessage());
                })
                .subscribe(
                        savedFilm -> log.debug("Saved film: {}", savedFilm.getImdbId()),
                        error -> log.error("Error in save subscription: {}", error.getMessage())
                );
    }

    @Cacheable(value = "preview_film")
    public Flux<PreviewFilmResponse> searchFilms(String title, String type, String year, Integer page) {
        var response = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("s", title)
                        .queryParamIfPresent("type", Optional.ofNullable(type))
                        .queryParamIfPresent("y", Optional.ofNullable(year))
                        .queryParamIfPresent("page", Optional.ofNullable(page))
                        .build())
                .retrieve()
                .bodyToFlux(PreviewFilmResponse.class);

        saveFilmsToDatabase(response);

        return response;
    }

}
