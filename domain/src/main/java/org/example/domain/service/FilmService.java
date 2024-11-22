package org.example.domain.service;

import org.example.domain.entity.FilmEntity;
import org.example.domain.model.FilmResponse;
import org.example.domain.properties.OMDBProperties;
import org.example.domain.repository.FilmRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.Optional;

@Service
public class FilmService {

    private final FilmRepository filmRepository;

    private final OMDBProperties properties;

    private final WebClient webClient;

    public FilmService(FilmRepository filmRepository, OMDBProperties properties, WebClient.Builder webClient) {
        this.filmRepository = filmRepository;
        this.properties = properties;
        this.webClient = webClient.baseUrl(properties.baseUrl()).build();
    }

    public void saveFilmsToDatabase(Flux<FilmResponse> filmResponses) {
        filmResponses.flatMap(filmResponse -> Flux.fromIterable(filmResponse.search()))
                .map(filmDTO -> new FilmEntity()
                        .setTitle(filmDTO.title())
                        .setYear(filmDTO.year())
                        .setImdbId(filmDTO.imdbId())
                        .setType(filmDTO.type())
                        .setPoster(filmDTO.poster()))
                .flatMap(filmRepository::save)
                .subscribe();
    }

    @Transactional
    public Flux<FilmResponse> searchFilms(String title, String type, String year, Integer page) {
        var response = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("s", title)
                        .queryParamIfPresent("type", Optional.ofNullable(type))
                        .queryParamIfPresent("y", Optional.ofNullable(year))
                        .queryParamIfPresent("page", Optional.ofNullable(page))
                        .build())
                .retrieve()
                .bodyToFlux(FilmResponse.class);

        saveFilmsToDatabase(response);

        return response;
    }

}
