package org.example.domain.repository;

import org.example.domain.entity.PreviewFilmEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PreviewFilmRepository extends JpaRepository<PreviewFilmEntity, Long> {

    // Mono<PreviewFilmEntity> findByImdbId(String imdbId);

    // New method to find multiple films by a list of imdbIds
   // List<PreviewFilmEntity> findAllByImdbId(Iterable<String> imdbIds);
}
