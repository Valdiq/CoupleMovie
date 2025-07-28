package org.example.webserver;

import org.example.domain.properties.AWSProperties;
import org.example.domain.properties.OMDBProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableConfigurationProperties({OMDBProperties.class, AWSProperties.class})
@EnableCaching
@EnableMongoRepositories(basePackages = {"org.example.logging.repository", "org.example.domain.repository.mongo"})
@EnableJpaRepositories(basePackages = {"org.example.domain.repository", "org.example.security.repository"})
@EntityScan(basePackages = {"org.example.domain.entity", "org.example.domain.document", "org.example.security.entity"})
@ComponentScan(basePackages = {
        "org.example.caching",
        "org.example.webserver",
        "org.example.domain",
        "org.example.logging",
        "org.example.security"
})
public class WebServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebServerApplication.class, args);
    }

}
