package org.example.logging.logger;

import lombok.RequiredArgsConstructor;
import org.example.logging.document.ServiceExceptionLogDocument;
import org.example.logging.repository.ServiceExceptionLogRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class MongoServiceLogger implements ServiceLogger {

    private final ServiceExceptionLogRepository serviceExceptionLogRepository;

    @Override
    public void log(String className, String exceptionName, String message, LocalDateTime dateTime) {
        serviceExceptionLogRepository.insert(new ServiceExceptionLogDocument()
                .setClassName(className)
                .setExceptionName(exceptionName)
                .setMessage(message)
                .setLocalDateTime(dateTime));
    }
}
