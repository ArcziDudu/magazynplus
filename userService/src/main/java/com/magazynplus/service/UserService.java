package com.magazynplus.service;

import com.magazynplus.dto.UserResponse;
import com.magazynplus.exception.UserNotFoundException;
import com.magazynplus.mapper.UserMapper;
import com.magazynplus.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserResponse findUserById(Integer userId) {
        return userMapper.mapFromEntity(userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(String.format("User with id [%s] does not exists!", userId))));
    }
}
