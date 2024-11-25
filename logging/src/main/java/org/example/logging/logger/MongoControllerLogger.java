package org.example.logging.logger;

import lombok.RequiredArgsConstructor;
import org.example.logging.document.ControllerExceptionLogDocument;
import org.example.logging.repository.ControllerExceptionLogRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MongoControllerLogger implements ControllerLogger {

    private final ControllerExceptionLogRepository controllerExceptionLogRepository;

    @Override
    public void log(HttpStatus status, String uri, Map<String, String[]> paramsMap, String className, String message, LocalDateTime dateTime) {
        controllerExceptionLogRepository.insert(new ControllerExceptionLogDocument()
                .setStatus(status)
                .setUri(uri)
                .setParamsMap(paramsMap)
                .setClassName(className)
                .setMessage(message)
                .setDateTime(dateTime));
    }
}
