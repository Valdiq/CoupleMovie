package org.example.logging.document;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@Accessors(chain = true)
@Document("execution_time_log")
public class ExecutionTimeLogDocument {

    @Id
    private String id;

    private String className;

    private String methodName;

    private Long executionTimeInMs;

    private Map<String, Object> argsMap;

    private LocalDateTime dateTime;
}
