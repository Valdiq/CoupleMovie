package org.example.couplemovie.properties;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Valid
@ConfigurationProperties(prefix = "omdb")
public record OMDBProperties(@NotBlank String apiKey, @NotBlank String baseUrl) {
}
