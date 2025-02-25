package com.example.apiJwtToken.service;

import com.example.apiJwtToken.model.User;
import com.example.apiJwtToken.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private BCryptPasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        passwordEncoder = new BCryptPasswordEncoder();
        //Inject the real password encoder into the service.
        userService = new UserService(userRepository, passwordEncoder);
    }

    @Test
    void registerUser_ShouldRegisterNewUser_WhenUsernameIsUnique() {
        // Arrange
        String username = "testUser";
        String password = "testPassword";

        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        User registeredUser = userService.registerUser(username, password);

        // Assert
        assertNotNull(registeredUser);
        assertEquals(username, registeredUser.getUsername());
        assertTrue(passwordEncoder.matches(password, registeredUser.getPassword()));
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void registerUser_ShouldThrowException_WhenUsernameAlreadyExists() {
        // Arrange
        String username = "existingUser";
        String password = "testPassword";

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(new User()));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> userService.registerUser(username, password));
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void registerUser_ShouldProperlyEncodePasswords() {
        String username = "anotherTestUser";
        String password = "anotherPassword";

        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        User registeredUser = userService.registerUser(username, password);

        assertTrue(passwordEncoder.matches(password, registeredUser.getPassword()));
    }
}