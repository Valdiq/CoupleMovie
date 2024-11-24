package org.example.domain.exception;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public record OMDBControllerExceptionResponse(HttpStatus status, String uri, String className, String message,
                                              LocalDateTime dateTime) {
}
