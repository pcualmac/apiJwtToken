package com.example.apiJwtToken.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class LogoutControllerTest {

    @Mock
    private SecurityContextLogoutHandler logoutHandler;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private Authentication authentication;

    @Mock
    private SecurityContext securityContext;

    @InjectMocks
    private LogoutController logoutController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    void testLogoutSuccess() {
        when(securityContext.getAuthentication()).thenReturn(authentication);

        ResponseEntity<String> responseEntity = logoutController.logout(request, response);

        verify(logoutHandler, times(1)).logout(request, response, authentication);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Logged out successfully", responseEntity.getBody());
    }

    @Test
    void testLogoutWhenNotAuthenticated() {
        when(securityContext.getAuthentication()).thenReturn(null);

        ResponseEntity<String> responseEntity = logoutController.logout(request, response);

        verify(logoutHandler, never()).logout(any(), any(), any());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Logged out successfully", responseEntity.getBody());
    }
}