package com.example.WeatherForecast.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.util.Collection;
import java.util.Map;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Around("execution(* com.example.WeatherForecast.service.*.*(..))")
    public Object logMethodExecution(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        String className = methodSignature.getDeclaringType().getSimpleName();
        String methodName = methodSignature.getName();

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        logger.info("Executing {}.{} with parameters: {}",
                className, methodName, joinPoint.getArgs());

        try {
            Object result = joinPoint.proceed();
            stopWatch.stop();

            logger.info("Completed {}.{} in {} ms with result: {}",
                    className, methodName, stopWatch.getTotalTimeMillis(), formatResult(result));

            return result;
        } catch (Exception e) {
            logger.error("Exception in {}.{}: {}",
                    className, methodName, e.getMessage(), e);
            throw e;
        }
    }

    private String formatResult(Object result) {
        switch (result) {
            case null -> {
                return "null";
            }
            case ResponseEntity<?> responseEntity -> {
                Object body = responseEntity.getBody();
                int statusCode = responseEntity.getStatusCode().value();

                if (body instanceof Map<?, ?> map) {
                    return String.format("Status: %d, Response size: %d entries", statusCode, map.size());
                } else if (body instanceof Collection<?> collection) {
                    return String.format("Status: %d, Response size: %d items", statusCode, collection.size());
                } else if (body != null) {
                    return String.format("Status: %d, Response type: %s", statusCode, body.getClass().getSimpleName());
                } else {
                    return String.format("Status: %d, Empty response", statusCode);
                }
            }


            // For non-ResponseEntity results
            case Collection<?> collection -> {
                return String.format("Collection with %d items", collection.size());
            }
            case Map<?, ?> map -> {
                return String.format("Map with %d entries", map.size());
            }
            default -> {
            }
        }

        return result.toString();
    }

}