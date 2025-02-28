package com.example.apiJwtToken.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class LogoutControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testLogoutSuccess() throws Exception {
        mockMvc.perform(post("/auth/logout") // Correct endpoint path
                .with(SecurityMockMvcRequestPostProcessors.user("user").roles("USER")))
                .andExpect(status().isOk());
    }

    @Test
    void testLogoutWhenNotAuthenticated() throws Exception {
        mockMvc.perform(post("/auth/logout")) // Correct endpoint path
                .andExpect(status().isForbidden()); // Expect 401
    }
}