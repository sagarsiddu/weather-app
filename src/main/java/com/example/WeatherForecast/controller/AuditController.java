package com.example.WeatherForecast.controller;

import com.example.WeatherForecast.dto.WeatherAuditDTO;
import com.example.WeatherForecast.service.WeatherAuditService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/audit/weather")
public class AuditController {

    private final WeatherAuditService auditService;

    public AuditController(WeatherAuditService auditService) {
        this.auditService = auditService;
    }

    @GetMapping("/{id}")
    public List<WeatherAuditDTO> getHistory(@PathVariable Long id) {
        return auditService.getAuditHistory(id);
    }

}
