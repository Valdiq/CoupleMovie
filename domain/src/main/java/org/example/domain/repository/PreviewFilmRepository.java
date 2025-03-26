package org.example.domain.repository;

import org.example.domain.entity.PreviewFilmEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface PreviewFilmRepository extends R2dbcRepository<PreviewFilmEntity, Long> {

    // Mono<PreviewFilmEntity> findByImdbId(String imdbId);

    // New method to find multiple films by a list of imdbIds
    Flux<PreviewFilmEntity> findAllByImdbId(Iterable<String> imdbIds);
}
