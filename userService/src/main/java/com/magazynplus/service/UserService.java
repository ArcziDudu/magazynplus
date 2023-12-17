package com.magazynplus.service;


import com.magazynplus.dto.UserResponse;
import com.magazynplus.entity.UserEntity;
import com.magazynplus.exception.UserNotFoundException;
import com.magazynplus.mapper.UserMapper;
import com.magazynplus.repository.UserRepository;
import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    public UserResponse findUserById(Integer userId) {
        Optional<UserEntity> byId = userRepository.findById(userId);
        return userMapper.mapFromEntity(userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(String.format("User with id [%s] does not exists!", userId))));
    }
}
