package com.magazynplus.service;


import com.magazynplus.dto.UserResponse;
import com.magazynplus.entity.UserEntity;
import com.magazynplus.events.NewUserRegisterDto;
import com.magazynplus.exception.UserNotFoundException;
import com.magazynplus.mapper.UserMapper;
import com.magazynplus.repository.UserRepository;
import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final ObservationRegistry observationRegistry;

    public UserResponse findUserByUsername(String username) {
        return userMapper.mapFromEntity(userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(String.format("User with username [%s] does not exists!", username))));
    }

    @KafkaListener(topics = "register")
    @Transactional
    public void handleRegisterNotification(NewUserRegisterDto newUserRegisterDto) {
        Observation.createNotStarted("on-message", this.observationRegistry).observe(() -> {
            userRepository.save(mapFromKeycloakRequest(newUserRegisterDto));
            log.info("New user has been created: " + newUserRegisterDto.getUsername());
        });
    }

    UserEntity mapFromKeycloakRequest(NewUserRegisterDto newUserRegisterDto) {
        return UserEntity.builder()
                .firstname(newUserRegisterDto.getFirstName())
                .lastname(newUserRegisterDto.getLastName())
                .email(newUserRegisterDto.getEmail())
                .username(newUserRegisterDto.getUsername())
                .products(List.of())
                .suppliers(Set.of())
                .build();
    }
}
