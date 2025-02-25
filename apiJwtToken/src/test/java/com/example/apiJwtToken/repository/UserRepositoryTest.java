package com.example.apiJwtToken.repository;

import com.example.apiJwtToken.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test") // Ensure you have a test profile
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void testSave_ShouldPersistUser() {
        // Arrange
        User user = new User();
        user.setUsername("testUser");
        user.setPassword("testPassword");

        // Act
        User savedUser = userRepository.save(user);

        // Assert
        assertNotNull(savedUser.getId());
        Optional<User> retrievedUser = userRepository.findById(savedUser.getId());
        assertTrue(retrievedUser.isPresent());
        assertEquals("testUser", retrievedUser.get().getUsername());
        assertEquals("testPassword", retrievedUser.get().getPassword());
    }

    @Test
    void testFindByUsername_ShouldReturnUser_WhenUsernameExists() {
        // Arrange
        User user = new User();
        user.setUsername("existingUser");
        user.setPassword("password");
        userRepository.save(user);

        // Act
        Optional<User> foundUser = userRepository.findByUsername("existingUser");

        // Assert
        assertTrue(foundUser.isPresent());
        assertEquals("existingUser", foundUser.get().getUsername());
    }

    @Test
    void testFindByUsername_ShouldReturnEmpty_WhenUsernameDoesNotExist() {
        // Act
        Optional<User> foundUser = userRepository.findByUsername("nonExistentUser");

        // Assert
        assertFalse(foundUser.isPresent());
    }

    @Test
    void testDelete_ShouldRemoveUser() {
        // Arrange
        User user = new User();
        user.setUsername("deleteUser");
        user.setPassword("password");
        User savedUser = userRepository.save(user);

        // Act
        userRepository.delete(savedUser);

        // Assert
        Optional<User> deletedUser = userRepository.findById(savedUser.getId());
        assertFalse(deletedUser.isPresent());
    }

    @Test
    void testUpdate_ShouldUpdateUser() {
        //Arrange
        User user = new User();
        user.setUsername("originalUsername");
        user.setPassword("originalPassword");
        User savedUser = userRepository.save(user);

        //Act
        savedUser.setUsername("updatedUsername");
        savedUser.setPassword("updatedPassword");
        userRepository.save(savedUser);

        //Assert
        Optional<User> updatedUser = userRepository.findById(savedUser.getId());
        assertTrue(updatedUser.isPresent());
        assertEquals("updatedUsername", updatedUser.get().getUsername());
        assertEquals("updatedPassword", updatedUser.get().getPassword());
    }
}