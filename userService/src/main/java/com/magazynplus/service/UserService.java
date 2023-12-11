package com.magazynplus.service;

import com.magazynplus.dto.ProductResponse;
import com.magazynplus.entity.ProductEntity;
import com.magazynplus.entity.UserEntity;
import com.magazynplus.exception.UserNotFoundException;
import com.magazynplus.mapper.ProductMapper;
import com.magazynplus.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final ProductMapper productMapper;
    public List<ProductResponse> findAllByUserId(Integer userId) {
        Optional<UserEntity> userById = userRepository.findById(userId);
        if (userById.isEmpty()) {
            throw new UserNotFoundException(String.format("User with id [%s] does not exists!", userId));
        } else {
            List<ProductEntity> products = userById.get().getProducts();
            return products.stream().map(productMapper::mapFromEntity).toList();
        }
    }
}
