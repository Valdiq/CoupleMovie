package org.example.domain.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Accessors(chain = true)
@Table("film")
public class FilmEntity {

    @Id
    private Long id;

    private String title;

    private String year;

    @Column("imdbId")
    private String imdbId;

    private String type;

    private String poster;

}
