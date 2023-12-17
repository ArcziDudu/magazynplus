package com.magazynplus.listener;

import com.magazynplus.event.NewSupplierEvent;
import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Component
@RequiredArgsConstructor
@Slf4j
public class NewSupplierCreatedEvent {

    private final KafkaTemplate<String, NewSupplierEvent> kafkaTemplate;
    private final ObservationRegistry observationRegistry;

    @EventListener
    public void handleOrderPlacedEvent(NewSupplierEvent event) {
        log.info("Order Placed Event Received, Sending OrderPlacedEvent to notificationTopic: {}", event.getSupplier());

        // Create Observation for Kafka Template
        try {
            Observation.createNotStarted("notification-topic", this.observationRegistry).observeChecked(() -> {
                CompletableFuture<SendResult<String, NewSupplierEvent>> future = kafkaTemplate.send("notificationTopic",
                        new NewSupplierEvent(event.getSupplier()));
                return future.handle((result, throwable) -> CompletableFuture.completedFuture(result));
            }).get();
        } catch (InterruptedException | ExecutionException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Error while sending message to Kafka", e);
        }
    }
}
