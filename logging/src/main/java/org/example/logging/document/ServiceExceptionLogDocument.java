package org.example.logging.document;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
@Document("service_exception_log")
public class ServiceExceptionLogDocument {

    @Id
    private String id;

    private String className;

    private String exceptionName;

    private String message;

    private LocalDateTime localDateTime;
}
