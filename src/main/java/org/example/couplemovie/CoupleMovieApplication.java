package org.example.couplemovie;

import org.example.couplemovie.properties.OMDBProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

@SpringBootApplication
@EnableConfigurationProperties(OMDBProperties.class)
@EnableCaching
@EnableR2dbcRepositories
public class CoupleMovieApplication {

    public static void main(String[] args) {
        SpringApplication.run(CoupleMovieApplication.class, args);
    }

}
