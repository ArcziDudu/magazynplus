package com.magazynplus.controller;

import com.magazynplus.service.FileProcessingService;
import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/file")
@RequiredArgsConstructor
@Slf4j
public class FileController {
    private final FileProcessingService fileProcessingService;
    private final ObservationRegistry observationRegistry;
    private String resultFormKafka;

    @KafkaListener(topics = "fileResponse")
    public void handleToken(String result) {
        Observation.createNotStarted("on-message", this.observationRegistry).observe(() -> {
      resultFormKafka = result;
            System.out.println(resultFormKafka);
        });
    }
    @PostMapping("/upload/{fileType}")
    public ResponseEntity<HttpStatus> uploadFile(@RequestParam("file") MultipartFile file,
                                                 @PathVariable String fileType,
                                                 HttpServletRequest request) {

        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String jwtTokenString = authorizationHeader.substring(7);
            if (fileType.equals("productsFile")) {
                fileProcessingService.createProductsFromCsvFile(file, jwtTokenString);

                ResponseEntity<HttpStatus> status = getHttpStatusResponseEntityFromKafkaResult();
                if (status != null) return status;
            }

            else if (fileType.equals("suppliersFile")) {
                fileProcessingService.createSuppliersFromCsvFile(file);
                ResponseEntity<HttpStatus> status = getHttpStatusResponseEntityFromKafkaResult();
                if (status != null) return status;
            }
        }
        throw new RuntimeException("No Authorization header found");
    }

    private ResponseEntity<HttpStatus> getHttpStatusResponseEntityFromKafkaResult() {
        if(resultFormKafka.equals("success")){
            return ResponseEntity.ok(HttpStatus.OK);
        } else if (resultFormKafka.equals("error")) {
            return ResponseEntity.ok(HttpStatus.BAD_REQUEST);
        }
        return null;
    }
}