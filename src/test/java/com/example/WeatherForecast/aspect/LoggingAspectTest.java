package com.example.WeatherForecast.aspect;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.LoggerFactory;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoggingAspectTest {

    private LoggingAspect loggingAspect;
    private ListAppender<ILoggingEvent> listAppender;

    @Mock
    private ProceedingJoinPoint joinPoint;

    @Mock
    private MethodSignature methodSignature;

    @BeforeEach
    void setUp() {
        loggingAspect = new LoggingAspect();

        // Setup logger
        Logger logger = (Logger) LoggerFactory.getLogger(LoggingAspect.class);
        listAppender = new ListAppender<>();
        listAppender.start();
        logger.addAppender(listAppender);
        logger.setAdditive(false); // Prevent logging to other appenders

        // Setup mocks
        when(joinPoint.getSignature()).thenReturn(methodSignature);
        when(methodSignature.getDeclaringType()).thenReturn(TestService.class);
        when(methodSignature.getName()).thenReturn("testMethod");
    }

    @Test
    void logMethodExecution_SuccessfulExecution() throws Throwable {
        // Arrange
        Object[] args = new Object[]{"testValue"};
        when(joinPoint.getArgs()).thenReturn(args);
        when(joinPoint.proceed()).thenReturn("result");

        // Act
        Object result = loggingAspect.logMethodExecution(joinPoint);

        // Assert
        assertEquals("result", result);

        List<ILoggingEvent> logsList = listAppender.list;

        // Verify execution start log
        ILoggingEvent startLog = logsList.get(0);
        assertEquals(Level.INFO, startLog.getLevel());
        assertEquals("Executing TestService.testMethod with parameters: [testValue]",
                startLog.getFormattedMessage());

        // Verify execution completion log
        ILoggingEvent completionLog = logsList.get(1);
        assertEquals(Level.INFO, completionLog.getLevel());
        assertTrue(completionLog.getFormattedMessage()
                .matches("Completed TestService.testMethod in \\d+ ms with result: result"));
    }

    @Test
    void logMethodExecution_ExceptionThrown() throws Throwable {
        // Arrange
        Object[] args = new Object[]{"testValue"};
        when(joinPoint.getArgs()).thenReturn(args);
        RuntimeException testException = new RuntimeException("Test exception");
        when(joinPoint.proceed()).thenThrow(testException);

        // Act & Assert
        assertThrows(RuntimeException.class, () ->
                loggingAspect.logMethodExecution(joinPoint)
        );

        List<ILoggingEvent> logsList = listAppender.list;

        // Verify execution start log
        ILoggingEvent startLog = logsList.get(0);
        assertEquals(Level.INFO, startLog.getLevel());
        assertEquals("Executing TestService.testMethod with parameters: [testValue]",
                startLog.getFormattedMessage());

        // Verify error log
        ILoggingEvent errorLog = logsList.get(1);
        assertEquals(Level.ERROR, errorLog.getLevel());
        assertEquals("Exception in TestService.testMethod: Test exception",
                errorLog.getFormattedMessage());
    }

    // Helper class for testing
    private static class TestService {
        public String testMethod(String param) {
            return param;
        }
    }
}