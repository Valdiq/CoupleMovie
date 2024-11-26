package org.example.domain.repository;

import org.example.domain.entity.PreviewFilmEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PreviewFilmRepository extends R2dbcRepository<PreviewFilmEntity, Long> {
}
