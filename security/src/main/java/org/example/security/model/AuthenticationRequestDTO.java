package org.example.security.model;

import jakarta.validation.constraints.NotBlank;
import org.example.security.util.ValidUsername;

public record AuthenticationRequestDTO(@NotBlank @ValidUsername String username, @NotBlank String password) {
}
