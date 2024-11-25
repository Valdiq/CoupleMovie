package org.example.logging.logger;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public interface ServiceLogger {
    public void log(String className, String exceptionName, String message, LocalDateTime dateTime);
}
