package org.example.couplemovie;

import org.example.couplemovie.properties.OMDBProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(OMDBProperties.class)
public class CoupleMovieApplication {

    public static void main(String[] args) {
        SpringApplication.run(CoupleMovieApplication.class, args);
    }

}
