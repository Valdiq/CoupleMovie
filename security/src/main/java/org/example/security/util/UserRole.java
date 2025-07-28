package org.example.security.util;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.NotNull;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Constraint(validatedBy = UserRoleValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@NotNull
public @interface UserRole {
    String message() default "Invalid User Role(s)";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
