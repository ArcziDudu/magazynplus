package com.magazynplus.service;


import com.magazynplus.entity.User;
import com.magazynplus.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User getLoggedUser() {
        JwtAuthenticationToken token = (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

        String email = String.valueOf(token.getTokenAttributes().get("email"));

        return userRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("Error while fetching user"));
    }

    public void syncUser(User user) {
        if (user == null) {
            throw new EntityNotFoundException("Error while user sync");
        }

        User saveUser = user;
        Optional<User> optionalUser = userRepository.findByEmail(user.getEmail());

        if (optionalUser.isPresent()) {
            saveUser = optionalUser.get();
            saveUser.setFirstname(user.getFirstname());
            saveUser.setLastname(user.getLastname());
        }

        userRepository.save(saveUser);
    }

}
