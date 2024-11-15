package org.example.couplemovie;

import org.example.couplemovie.properties.OMDBProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableConfigurationProperties(OMDBProperties.class)
@EnableCaching
public class CoupleMovieApplication {

    public static void main(String[] args) {
        SpringApplication.run(CoupleMovieApplication.class, args);
    }

}
