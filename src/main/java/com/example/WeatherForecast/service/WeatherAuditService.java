package com.example.WeatherForecast.service;

import com.example.WeatherForecast.dto.WeatherAuditDTO;
import com.example.WeatherForecast.model.WeatherResponse;
import org.javers.core.Javers;
import org.javers.core.metamodel.object.CdoSnapshot;

import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.javers.repository.jql.QueryBuilder.byInstanceId;

@Service
public class WeatherAuditService {

    private final Javers javers;

    public WeatherAuditService(Javers javers) {
        this.javers = javers;
    }

    public List<WeatherAuditDTO> getAuditHistory(Long id) {
        List<CdoSnapshot> snapshots = javers.findSnapshots(
                byInstanceId(id, WeatherResponse.class).build()
        );

        return snapshots.stream().map(snapshot -> {
            Map<String, Object> stateMap = new HashMap<>();
            snapshot.getState().getPropertyNames().forEach(name -> {
                Object value = snapshot.getState().getPropertyValue(name);
                stateMap.put(name, value);
            });

            Instant commitInstant = snapshot.getCommitMetadata()
                    .getCommitDate()
                    .atZone(ZoneId.systemDefault())
                    .toInstant();

            return new WeatherAuditDTO(
                    snapshot.getCommitMetadata().getId().toString(),
                    commitInstant,
                    snapshot.getCommitMetadata().getAuthor(),
                    stateMap
            );
        }).collect(Collectors.toList());
    }
}

