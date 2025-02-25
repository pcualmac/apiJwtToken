package com.example.apiJwtToken.controller;

import com.example.apiJwtToken.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content; // Added import
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private UserController userController;

    @MockBean
    private UserService userService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    @WithMockUser
    void testRegisterUser_ShouldReturnSuccessMessage() throws Exception {
        // Arrange
        String username = "testUser";
        String password = "testPassword";

        when(userService.registerUser(anyString(), anyString())).thenReturn(null);

        // Act & Assert
        mockMvc.perform(post("/api/users/register")
                        .param("username", username)
                        .param("password", password))
                .andExpect(status().isOk())
                .andExpect(content().string("User registered successfully"));

        verify(userService, times(1)).registerUser(username, password);
    }

    @Test
    @WithMockUser
    void testRegisterUser_ShouldHandleExceptionFromService() throws Exception {
        // Arrange
        String username = "testUser";
        String password = "testPassword";

        doThrow(new RuntimeException("Registration failed")).when(userService).registerUser(username, password);

        // Act & Assert
        mockMvc.perform(post("/api/users/register")
                        .param("username", username)
                        .param("password", password))
                .andExpect(status().isInternalServerError());

        verify(userService, times(1)).registerUser(username, password);
    }

    @Test
    @WithMockUser
    void testRegisterUser_ShouldHandleMissingParameters() throws Exception {
        // Arrange
        String username = "testUser";

        // Act & Assert
        mockMvc.perform(post("/api/users/register")
                        .param("username", username))
                .andExpect(status().isBadRequest());

        mockMvc.perform(post("/api/users/register")
                        .param("password", "testPassword"))
                .andExpect(status().isBadRequest());
    }
}