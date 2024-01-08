package com.magazynplus.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.magazynplus.entity.ProductEntity;
import com.magazynplus.entity.SupplierEntity;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class FileProcessingService {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public void createProductsFromCsvFile(MultipartFile file, String jwtTokenString) {
        log.info("File service received file to process");
        kafkaTemplate.send("token", jwtTokenString);
        try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            CsvToBean<ProductEntity> csvToBean = new CsvToBeanBuilder<ProductEntity>(reader)
                    .withType(ProductEntity.class)
                    .build();

            List<ProductEntity> products = csvToBean.parse();
            log.info("file successfully processed to products array");
            String productsJson = objectMapper.writeValueAsString(products);
            kafkaTemplate.send("fileProcessed", productsJson);
            log.info("Products sent to Kafka topic: {}", "fileProcessed");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createSuppliersFromCsvFile(MultipartFile file) {
        log.info("File service received file to process");
        try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            CsvToBean<SupplierEntity> csvToBean = new CsvToBeanBuilder<SupplierEntity>(reader)
                    .withType(SupplierEntity.class)
                    .build();

            List<SupplierEntity> suppliers = csvToBean.parse();
            log.info("file successfully processed to suppliers array");
            String productsJson = objectMapper.writeValueAsString(suppliers);
            kafkaTemplate.send("suppliersFromFile", productsJson);
            log.info("Suppliers sent to Kafka topic: {}", "fileProcessed");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
