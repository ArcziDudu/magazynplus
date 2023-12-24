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
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final ObservationRegistry observationRegistry;
    public UserResponse findUserById(Integer userId) {
        Optional<UserEntity> byId = userRepository.findById(userId);
        return userMapper.mapFromEntity(userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(String.format("User with id [%s] does not exists!", userId))));
    }
    @KafkaListener(topics = "register")
    @Transactional
    public void handleRegisterNotification(NewUserRegisterDto newUserRegisterDto) {
        Observation.createNotStarted("on-message", this.observationRegistry).observe(() -> {
            System.out.println(newUserRegisterDto.getFirstName());
            System.out.println(newUserRegisterDto.getLastName());
            System.out.println(newUserRegisterDto.getUsername());
            System.out.println(newUserRegisterDto.getEmail());
           userRepository.save(mapFromKeycloakRequest(newUserRegisterDto));
           log.info("New user has been created: "+ newUserRegisterDto.getUsername());
        });
    }
    UserEntity mapFromKeycloakRequest(NewUserRegisterDto newUserRegisterDto){
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
