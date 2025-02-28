package com.example.apiJwtToken.controller;

import com.example.apiJwtToken.dto.UserRequest;
import com.example.apiJwtToken.model.User;
import com.example.apiJwtToken.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @Test
    void registerUser_Success() {
        UserRequest userRequest = new UserRequest("testUser", "password");
        User mockUser = new User();
        mockUser.setUsername("testUser");
        
        when(userService.registerUser("testUser", "password")).thenReturn(mockUser);

        ResponseEntity<?> response = userController.registerUser(userRequest);

        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody().toString().contains("User registered successfully"));
    }

    @Test
    void registerUser_UsernameAlreadyTaken() {
        UserRequest userRequest = new UserRequest("existingUser", "password");

        when(userService.registerUser("existingUser", "password"))
                .thenThrow(new RuntimeException("Username already taken"));

        ResponseEntity<?> response = userController.registerUser(userRequest);

        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Username already taken", response.getBody());
    }
}
