package com.example.WeatherForecast.repo;

import com.example.WeatherForecast.model.WeatherResponse;
import org.javers.core.Javers;
import org.javers.core.metamodel.object.CdoSnapshot;
import org.javers.repository.jql.QueryBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class JaversTest {

    @Autowired
    private Javers javers;

    @Autowired
    private WeatherRepository repo;

//    @Test
//    @Transactional
//    void testSnapshotAuditing() {
//        WeatherResponse w1 = new WeatherResponse("Delhi", "Sunny", 32.0, 20.0, LocalDateTime.now());
//        WeatherResponse w2 = new WeatherResponse("Mumbai", "Cloudy", 35.0, 60.0, LocalDateTime.now());
//
//        repo.save(w1);
//        repo.save(w2);
//        repo.flush(); // 🔥 Force DB flush so Javers captures changes
//
//        List<CdoSnapshot> snapshots = javers.findSnapshots(QueryBuilder.byClass(WeatherResponse.class).build());
//        snapshots.forEach(System.out::println);
//
//        assertEquals(2, snapshots.size(), "Expected 2 snapshots (1 for each insert)");
//    }
}



