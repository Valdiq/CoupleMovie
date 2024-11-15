package org.example.couplemovie.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.redis.core.RedisHash;

@Entity
@Table(name = "film")
@Data
@RedisHash("Film")
public class FilmEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String year;

    private String imdbId;

    private String type;

    private String poster;

}
