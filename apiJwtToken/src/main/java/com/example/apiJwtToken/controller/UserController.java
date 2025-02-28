package com.example.apiJwtToken.controller;

import com.example.apiJwtToken.dto.UserRequest;
import com.example.apiJwtToken.model.User;
import com.example.apiJwtToken.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserRequest userRequest) {
        try {
            User user = userService.registerUser(userRequest.getUsername(), userRequest.getPassword());
            return ResponseEntity.ok("User registered successfully: " + user.getUsername());
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}