package org.example.domain.repository;

import org.example.domain.entity.ExpandedFilmEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpandedFilmRepository extends R2dbcRepository<ExpandedFilmEntity, Long> {
}
