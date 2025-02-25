package com.example.apiJwtToken.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setUsername("testUser");
    }

    @Test
    void testSetPassword_ShouldEncodePassword() {
        String rawPassword = "password123";
        user.setPassword(rawPassword);

        assertNotNull(user.getPassword());
        assertTrue(new BCryptPasswordEncoder().matches(rawPassword, user.getPassword()));
    }

    @Test
    void testGetId_SetId_ShouldGetSetId() {
        user.setId(1L);
        assertEquals(1L, user.getId());
    }

    @Test
    void testGetUsername_SetUsername_ShouldGetSetUsername() {
        assertEquals("testUser", user.getUsername());
        user.setUsername("newUser");
        assertEquals("newUser", user.getUsername());
    }

    @Test
    void testNoArgsConstructor_ShouldCreateObjectWithDefaults() {
        User emptyUser = new User();
        assertNull(emptyUser.getId());
        assertNull(emptyUser.getUsername());
        assertNull(emptyUser.getPassword());
    }

    @Test
    void testAllArgsConstructor_ShouldCreateObjectWithValues() {
        User fullUser = new User(1L, "fullUser", "encodedPassword");
        assertEquals(1L, fullUser.getId());
        assertEquals("fullUser", fullUser.getUsername());
        assertEquals("encodedPassword", fullUser.getPassword());
    }

    @Test
    void testSetPassword_ShouldHandleNull() {
        // Test that it does not throw an exception, and the password is not null.
        assertThrows(IllegalArgumentException.class, () -> user.setPassword(null));
    }

    @Test
    void testSetPassword_ShouldHandleEmptyString() {
        user.setPassword("");
        assertNotNull(user.getPassword());
    }
}