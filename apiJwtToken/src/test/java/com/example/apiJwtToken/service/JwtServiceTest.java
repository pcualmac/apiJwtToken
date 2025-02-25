package com.example.apiJwtToken.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.apiJwtToken.config.AppJwtProperties;

import java.util.Date;
import java.util.Base64;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = AppJwtProperties.class)
@EnableConfigurationProperties(AppJwtProperties.class)
class JwtServiceTest {

    @Autowired
    private AppJwtProperties appJwtProperties;
    private JwtService jwtService;
    // private final String secretKey = appJwtProperties.getSecret(); // Replace with your generated key
    // private final long expirationMs = Long.parseLong(appJwtProperties.getExpirationMs());

    @BeforeEach
    void setUp() {
        jwtService = new JwtService(appJwtProperties.getSecret(), Long.parseLong(appJwtProperties.getExpirationMs()));
    }

    @Test
    void generateToken_ShouldReturnValidToken() {
        UserDetails userDetails = new User("testUser", "password", new java.util.ArrayList<>());
        String token = jwtService.generateToken(userDetails);

        assertNotNull(token);

        Claims claims = Jwts.parser()
                .setSigningKey(Base64.getDecoder().decode(appJwtProperties.getSecret()))
                .parseClaimsJws(token)
                .getBody();

        assertEquals("testUser", claims.getSubject());
        assertTrue(claims.getExpiration().after(new Date()));
    }
}