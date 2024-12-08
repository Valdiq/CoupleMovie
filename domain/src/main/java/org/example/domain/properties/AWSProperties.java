package org.example.domain.properties;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Valid
@ConfigurationProperties(prefix = "aws")
public record AWSProperties(@NotBlank String accessKey, @NotBlank String secretKey, @NotBlank String bucketName) {
}
