package com.example.apiJwtToken.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class UserTest {

    @Test
    void testNoArgsConstructor() {
        User user = new User();
        assertNull(user.getId());
        assertNull(user.getUsername());
        assertNull(user.getPassword());
    }

    @Test
    void testAllArgsConstructor() {
        Long id = 1L;
        String username = "testUser";
        String password = "testPassword";

        User user = new User(id, username, password);

        assertEquals(id, user.getId());
        assertEquals(username, user.getUsername());
        assertEquals(password, user.getPassword());
    }

    @Test
    void testGettersAndSetters() {
        User user = new User();

        Long id = 1L;
        String username = "testUser";
        String password = "testPassword";

        user.setId(id);
        user.setUsername(username);
        user.setPassword(password);

        assertEquals(id, user.getId());
        assertEquals(username, user.getUsername());
        assertEquals(password, user.getPassword());
    }
}