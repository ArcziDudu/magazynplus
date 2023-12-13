package com.magazynplus.controller;

import com.magazynplus.dto.UserResponse;
import com.magazynplus.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping(value = "/info/{userId}")
    public ResponseEntity<UserResponse> fetchUserData(@PathVariable Integer userId) {
        return ResponseEntity.ok(userService.findUserById(userId));
    }

    @GetMapping(value = "/product/find/{searchKey}")
    public ResponseEntity<String> fetchUserData(@PathVariable String searchKey) {
        return ResponseEntity.ok(searchKey);
    }
}
