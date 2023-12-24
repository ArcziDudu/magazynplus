package com.magazynplus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.config.GlobalCorsProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.cors.CorsConfiguration;

@SpringBootApplication
public class ApiGatewayApplication {
	public static void main(String[] args) {
		SpringApplication.run(ApiGatewayApplication.class, args);
	}
	@Bean
	public GlobalCorsProperties myCustomGlobalCorsProperties() {
		GlobalCorsProperties globalCorsProperties = new GlobalCorsProperties();
		globalCorsProperties.getCorsConfigurations().put("/**", buildCorsConfiguration());
		return globalCorsProperties;
	}


	private CorsConfiguration buildCorsConfiguration() {
		CorsConfiguration corsConfiguration = new CorsConfiguration();
		corsConfiguration.addAllowedOrigin("http://localhost:4200");
		corsConfiguration.addAllowedHeader("*");
		corsConfiguration.addAllowedMethod("*");
		corsConfiguration.setMaxAge(3600L);
		corsConfiguration.setAllowCredentials(true);
		return corsConfiguration;
	}
}

