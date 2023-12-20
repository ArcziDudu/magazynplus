package com.magazynplus;

import com.magazynplus.event.NewSupplierCreatedEvent;
import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@RequiredArgsConstructor
@Slf4j
public class userServiceApplication {
    private final ObservationRegistry observationRegistry;
    public static void main(String[] args) {
        SpringApplication.run(userServiceApplication.class, args);
    }

}
