package org.example.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import jakarta.persistence.*;

@Entity
@Table(name = "preview_film")
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class PreviewFilmEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String year;

    @Column(name = "imdb_id")
    private String imdbId;

    private String type;

    private String poster;

}