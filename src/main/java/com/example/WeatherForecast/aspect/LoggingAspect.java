package com.example.WeatherForecast.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

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
                    className, methodName, stopWatch.getTotalTimeMillis(), result);

            return result;
        } catch (Exception e) {
            logger.error("Exception in {}.{}: {}",
                    className, methodName, e.getMessage(), e);
            throw e;
        }
    }
}