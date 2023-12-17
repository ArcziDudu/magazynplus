package com.magazynplus.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

@TestConfiguration
public class WebClientTestConfig {
    @Bean
    public WebClient.Builder testWebClientBuilder() {
        return WebClient.builder()
                .baseUrl("http://localhost:8089"); // WireMock port
    }
}