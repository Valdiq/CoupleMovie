package org.example.webserver;

import org.example.domain.properties.OMDBProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

@SpringBootApplication
@EnableConfigurationProperties(OMDBProperties.class)
@EnableCaching
@EnableR2dbcRepositories(basePackages = "org.example.domain.repository")
@EnableMongoRepositories(basePackages = {"org.example.logging.repository", "org.example.domain.repository"})
@ComponentScan(basePackages = {
        "org.example.caching",
        "org.example.webserver",
        "org.example.domain",
        "org.example.logging"
})
public class WebServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebServerApplication.class, args);
    }

}
