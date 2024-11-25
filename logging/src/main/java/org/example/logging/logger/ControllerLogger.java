package org.example.logging.logger;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Map;

@Component
public interface ControllerLogger {
    public void log(HttpStatus status, String uri, Map<String, String[]> paramsMap, String className, String message, LocalDateTime dateTime);
}
