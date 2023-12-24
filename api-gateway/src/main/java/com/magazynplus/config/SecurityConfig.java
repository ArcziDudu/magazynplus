package com.magazynplus.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
@EnableWebFluxSecurity
@RequiredArgsConstructor
@EnableScheduling
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http){
        http
                .cors().configurationSource(request -> {
                    CorsConfiguration configuration = new CorsConfiguration();
                    configuration.setAllowedOrigins(List.of("http://localhost:4200"));
                    configuration.setAllowedMethods(List.of("*"));
                    configuration.setAllowedHeaders(List.of("*"));
                    return configuration;
                })
                .and()
                .authorizeExchange(exchanges -> exchanges
                        .anyExchange().authenticated());
        http.oauth2ResourceServer((oauth2) -> oauth2.jwt(Customizer.withDefaults()));;
        http.csrf().disable();
        return http.build();
    }
    @Bean
    public GatewayFilter userExtractionFilter() {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            String authorizationHeader = request.getHeaders().getFirst("Authorization");

            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                ServerHttpRequest mutatedRequest = request.mutate()
                        .header("Authorization", authorizationHeader)
                        .build();

                return chain.filter(exchange.mutate().request(mutatedRequest).build());
            } else {
                return chain.filter(exchange);
            }
        };}
    @Bean
    public RouteLocator myRoutes(RouteLocatorBuilder builder, GatewayFilter userExtractionFilter) {
        return builder.routes()
                .route(r -> r.path("/api/product/**")
                        .filters(f -> f.filter(userExtractionFilter))
                        .uri("lb://product-service"))
                .route(r -> r.path("/api/supplier/**")
                        .filters(f -> f.filter(userExtractionFilter))
                        .uri("lb://supplier-service"))
                .build();
    }


}