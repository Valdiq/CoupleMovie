package org.example.domain.repository;

import org.example.domain.entity.ExpandedFilmEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface ExpandedFilmRepository extends R2dbcRepository<ExpandedFilmEntity, Long> {

    @Query("SELECT * FROM expanded_film LIMIT 1 OFFSET :offset")
    public Mono<ExpandedFilmEntity> findRandomFilm(long offset);
}
