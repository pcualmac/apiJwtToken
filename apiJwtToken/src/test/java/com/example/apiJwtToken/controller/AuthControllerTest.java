package com.example.apiJwtToken.controller;

import com.example.apiJwtToken.dto.AuthRequest;
import com.example.apiJwtToken.service.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class AuthControllerTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAuthenticateAndGetToken_Success() {
        AuthRequest authRequest = new AuthRequest();
        authRequest.setUsername("testuser");
        authRequest.setPassword("testpassword");

        UserDetails userDetails = User.withUsername("testuser").password("testpassword").roles("USER").build();
        Authentication authentication = mock(Authentication.class);

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(authentication.isAuthenticated()).thenReturn(true);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(jwtService.generateToken(userDetails)).thenReturn("testtoken");

        ResponseEntity<String> response = authController.authenticateAndGetToken(authRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("testtoken", response.getBody());
        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtService, times(1)).generateToken(userDetails);
    }

    @Test
    public void testAuthenticateAndGetToken_InvalidCredentials() {
        AuthRequest authRequest = new AuthRequest();
        authRequest.setUsername("invaliduser");
        authRequest.setPassword("invalidpassword");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new BadCredentialsException("Invalid credentials"));


        ResponseEntity<String> response = authController.authenticateAndGetToken(authRequest);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    public void testAuthenticateAndGetToken_AuthenticationFailure() {
        AuthRequest authRequest = new AuthRequest();
        authRequest.setUsername("testuser");
        authRequest.setPassword("wrongpassword");

        Authentication authentication = mock(Authentication.class);

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(authentication.isAuthenticated()).thenReturn(false);

        ResponseEntity<String> response = authController.authenticateAndGetToken(authRequest);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("Invalid credentials", response.getBody());
        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtService, never()).generateToken(any());
    }

    @Test
    public void testAuthenticateAndGetToken_NullAuthRequest() {
        ResponseEntity<String> response = authController.authenticateAndGetToken(null);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void testAuthenticateAndGetToken_NullUsername() {
        AuthRequest authRequest = new AuthRequest();
        authRequest.setPassword("testpassword");
        ResponseEntity<String> response = authController.authenticateAndGetToken(authRequest);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

   @Test
    public void testAuthenticateAndGetToken_NullPassword() {
        AuthRequest authRequest = new AuthRequest();
        authRequest.setUsername("testuser");
        ResponseEntity<String> response = authController.authenticateAndGetToken(authRequest);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Password cannot be null or empty", response.getBody());
    }
}