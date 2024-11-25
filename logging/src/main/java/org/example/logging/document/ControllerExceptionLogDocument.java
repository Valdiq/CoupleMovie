package org.example.logging.document;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@Accessors(chain = true)
@Document("controller_exception_log")
public class ControllerExceptionLogDocument {

    @Id
    private String id;

    private HttpStatus status;

    private String uri;

    private Map<String, String[]> paramsMap;

    private String className;

    private String message;

    private LocalDateTime dateTime;
}
