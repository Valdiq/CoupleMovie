package org.example.security.exception;

import java.time.LocalDateTime;


public record UserControllerExceptionResponse(int status, String path, String exceptionName, String message,
                                              LocalDateTime timestamp) {
}
