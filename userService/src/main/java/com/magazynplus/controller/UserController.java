package com.magazynplus.controller;

import com.magazynplus.dto.UserResponse;
import com.magazynplus.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping(value = "/info/{userId}")
    public ResponseEntity<UserResponse> fetchUserData(@PathVariable Integer userId) {
        return ResponseEntity.ok(userService.findUserById(userId));
    }
}
