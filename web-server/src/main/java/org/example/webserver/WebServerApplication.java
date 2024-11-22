package org.example.webserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.example.domain.properties.OMDBProperties;

@SpringBootApplication
@EnableConfigurationProperties(OMDBProperties.class)
@EnableCaching
@EnableR2dbcRepositories(basePackages = "org.example.domain.repository")
@ComponentScan(basePackages = {
    "org.example.caching",
    "org.example.webserver",
    "org.example.domain"
})
public class WebServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebServerApplication.class, args);
    }

}
