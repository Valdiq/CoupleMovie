package org.example.security.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.example.security.util.ValidUsername;


public record RegisterRequestDTO(@NotBlank @Email String email, @NotBlank @ValidUsername String username,
                                 @NotBlank String password) {
}
