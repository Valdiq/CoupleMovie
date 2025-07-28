package org.example.domain.repository;

import org.example.domain.entity.ExpandedFilmEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExpandedFilmRepository extends JpaRepository<ExpandedFilmEntity, Long> {

    @Query(value = "SELECT * FROM expanded_film LIMIT 1 OFFSET :offset", nativeQuery = true)
    public Optional<ExpandedFilmEntity> findRandomFilm(@Param("offset") long offset);
}
