package org.example.security.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.example.security.util.UserRole;
import org.example.security.util.UserRoles;
import org.example.security.util.ValidUsername;

import java.util.Set;


public record RegisterRequestDTO(@NotBlank @Email String email, @NotBlank @ValidUsername String username,
                                 @NotBlank String password, @UserRole Set<UserRoles> roles) {
    @Override
    public Set<UserRoles> roles() {
        return Set.copyOf(roles);
    }
}
