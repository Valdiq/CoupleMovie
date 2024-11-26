package org.example.domain.entity;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Accessors(chain = true)
@Table("expanded_film")
public class ExpandedFilmEntity {

    @Id
    private Long id;

    private String title;

    private String year;

    private String released;

    private String runtime;

    private String genre;

    private String director;

    private String writer;

    private String actors;

    private String plot;

    private String language;

    private String country;

    private String awards;

    private String poster;

    @Column("imdb_rating")
    private String imdbRating;

    @Column("imdb_votes")
    private String imdbVotes;

    @Column("imdb_id")
    private String imdbId;

    private String type;

}