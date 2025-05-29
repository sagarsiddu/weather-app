package com.example.WeatherForecast.service;

import com.example.WeatherForecast.dto.WeatherAuditDTO;
import com.example.WeatherForecast.model.WeatherResponse;
import com.example.WeatherForecast.repo.WeatherRepository;
import org.javers.repository.sql.JaversSqlRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource("classpath:application-test.properties")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class JaversAuditIntegrationTest {

    @Autowired
    private WeatherRepository repository;

    @Autowired
    private WeatherAuditService auditService;

    @Autowired
    private JaversSqlRepository javersSqlRepository;

    @Test
    @Transactional
    public void testEnsureSchema() {
        javersSqlRepository.ensureSchema(); // now inside transaction
    }

    @Test
    @Transactional
    public void testAuditSnapshots() {
        WeatherResponse response = new WeatherResponse();
        response.setCity("Mumbai");
        response.setTemperature(30.0);
        response.setHumidity(70.0);
        response.setDescription("Sunny");
        response.setTimestamp(LocalDateTime.now());

        response = repository.save(response);
        repository.flush(); // ensure it's committed

        response.setTemperature(32.5);
        response = repository.save(response);
        repository.flush();

        List<WeatherAuditDTO> snapshots = auditService.getAuditHistory(response.getId());
        assertEquals(2, snapshots.size(), "Expected 2 snapshots (initial + update)");
    }
}



