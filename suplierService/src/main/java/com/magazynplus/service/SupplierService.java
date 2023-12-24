package com.magazynplus.service;


import com.magazynplus.dto.SupplierRequest;
import com.magazynplus.entity.SupplierEntity;
import com.magazynplus.events.NewSupplierEvent;
import com.magazynplus.repository.SupplierRepository;
import com.magazynplus.repository.mapper.SupplierMapper;
import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class SupplierService {
    private final SupplierRepository supplierRepository;
    private final SupplierMapper supplierMapper;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final ObservationRegistry observationRegistry;

    @Transactional
    public void createSupplier(SupplierRequest request) {

        SupplierEntity supplierEntity = supplierMapper.mapFromRequest(request);
        Observation inventoryServiceObservation = Observation.createNotStarted("inventory-service-lookup",
                this.observationRegistry);
        inventoryServiceObservation.lowCardinalityKeyValue("call", "inventory-service");
        inventoryServiceObservation.observe(() -> {
            supplierRepository.save(supplierEntity);
            applicationEventPublisher.publishEvent(new NewSupplierEvent(this, supplierEntity));
        });
    }


}
