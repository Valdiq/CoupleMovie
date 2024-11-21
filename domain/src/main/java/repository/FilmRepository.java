package repository;

import entity.FilmEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FilmRepository extends R2dbcRepository<FilmEntity, Long> {
}
