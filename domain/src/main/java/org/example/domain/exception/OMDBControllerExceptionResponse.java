package org.example.domain.exception;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public record OMDBControllerExceptionResponse(HttpStatus status, String uri, Map<String, String[]> paramsMap,
                                              String className, String message,
                                              LocalDateTime dateTime) {

    public OMDBControllerExceptionResponse(HttpStatus status, String uri, Map<String, String[]> paramsMap, String className, String message, LocalDateTime dateTime) {
        if (paramsMap == null) {
            this.paramsMap = new HashMap<>();
        } else {
            this.paramsMap = Map.copyOf(paramsMap);
        }
        this.status = status;
        this.uri = uri;
        this.className = className;
        this.message = message;
        this.dateTime = dateTime;
    }

    @Override
    public Map<String, String[]> paramsMap() {
        return Map.copyOf(paramsMap);
    }
}
