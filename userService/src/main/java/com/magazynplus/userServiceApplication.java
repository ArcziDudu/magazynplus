package com.magazynplus;

import com.magazynplus.event.NewSupplierCreatedEvent;
import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;

@SpringBootApplication
@RequiredArgsConstructor
@Slf4j
public class userServiceApplication {
    private final ObservationRegistry observationRegistry;
    public static void main(String[] args) {
        SpringApplication.run(userServiceApplication.class, args);
    }
    @KafkaListener(topics = "notificationTopic")
    public void handleNotification(NewSupplierCreatedEvent newSupplierCreatedEvent) {
        Observation.createNotStarted("on-message", this.observationRegistry).observe(() -> {
            log.info("Got message <{}>", newSupplierCreatedEvent);
        });
        // send out an email notification
    }
}
