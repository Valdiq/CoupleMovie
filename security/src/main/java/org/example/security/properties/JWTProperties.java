package org.example.security.properties;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Valid
@ConfigurationProperties(prefix = "jwt")
public record JWTProperties(@NotBlank String secret, @NotBlank Long expirationTimeMs) {}

