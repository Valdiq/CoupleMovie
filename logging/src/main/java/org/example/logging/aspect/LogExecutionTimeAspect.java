package org.example.logging.aspect;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.example.logging.document.ExecutionTimeLogDocument;
import org.example.logging.log_target.LogExecutionTime;
import org.example.logging.repository.ExecutionTimeLogRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Aspect
@RequiredArgsConstructor
@Component
public class LogExecutionTimeAspect {

    private final ExecutionTimeLogRepository executionTimeLogRepository;

    @Around("@annotation(logExecutionTime)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint, LogExecutionTime logExecutionTime) throws Throwable {

        Object proceeded;
        long startTime = System.currentTimeMillis();

        try {
            proceeded = joinPoint.proceed();
        } finally {
            long executionTime = System.currentTimeMillis() - startTime;
                        
            executionTimeLogRepository.insert(new ExecutionTimeLogDocument()
                    .setExecutionTimeInMs(executionTime)
                    .setDateTime(LocalDateTime.now())
                    .setMethodName(joinPoint.getSignature().getName())
                    .setArgsMap(createArgsMap(joinPoint))
                    .setClassName(joinPoint.getSignature().getDeclaringTypeName()));
        }
        return proceeded;
    }
    
    private Map<String, Object> createArgsMap(ProceedingJoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();

        Map<String, Object> argsMap = new HashMap<>();
        Object[] argsValues = joinPoint.getArgs();
        String[] argsNames = signature.getParameterNames();

        for (int i = 0; i < argsValues.length; i++) {
            argsMap.put(argsNames[i], argsValues[i]);
        }

        return argsMap;
    }
    
}
