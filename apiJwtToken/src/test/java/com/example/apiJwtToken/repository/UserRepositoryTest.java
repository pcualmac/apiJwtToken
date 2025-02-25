package com.example.apiJwtToken.repository;

import com.example.apiJwtToken.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test") // Optional: Activate a test profile if needed
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void testFindByUsername_ShouldReturnUser_WhenUsernameExists() {
        // Arrange
        User user = new User();
        user.setUsername("testUser");
        user.setPassword("password");
        entityManager.persist(user);
        entityManager.flush();

        // Act
        Optional<User> foundUser = userRepository.findByUsername("testUser");

        // Assert
        assertTrue(foundUser.isPresent());
        assertEquals("testUser", foundUser.get().getUsername());
    }

    @Test
    void testFindByUsername_ShouldReturnEmptyOptional_WhenUsernameDoesNotExist() {
        // Act
        Optional<User> foundUser = userRepository.findByUsername("nonExistentUser");

        // Assert
        assertFalse(foundUser.isPresent());
    }

    @Test
    void testSave_ShouldPersistUser() {
        // Arrange
        User user = new User();
        user.setUsername("newUser");
        String rawPassword = "newPassword"; // Store the raw password

        // Act
        user.setPassword(rawPassword); // Encode the password before saving.
        User savedUser = userRepository.save(user);

        // Assert
        assertNotNull(savedUser.getId());
        assertEquals("newUser", savedUser.getUsername());
        assertTrue(new BCryptPasswordEncoder().matches(rawPassword, savedUser.getPassword())); // Verify encoding

        User retrievedUser = entityManager.find(User.class, savedUser.getId());
        assertNotNull(retrievedUser);
        assertEquals("newUser", retrievedUser.getUsername());
        assertTrue(new BCryptPasswordEncoder().matches(rawPassword, retrievedUser.getPassword())); //Verify encoding.
    }
    
    @Test
    void testDelete_ShouldRemoveUser() {
        // Arrange
        User user = new User();
        user.setUsername("deleteUser");
        user.setPassword("deletePassword");
        entityManager.persist(user);
        entityManager.flush();

        // Act
        userRepository.delete(user);

        // Assert
        User deletedUser = entityManager.find(User.class, user.getId());
        assertNull(deletedUser);
    }

    @Test
    void testFindById_ShouldReturnUser_WhenIdExists() {
        // Arrange
        User user = new User();
        user.setUsername("idUser");
        user.setPassword("idPassword");
        entityManager.persist(user);
        entityManager.flush();

        // Act
        Optional<User> foundUser = userRepository.findById(user.getId());

        // Assert
        assertTrue(foundUser.isPresent());
        assertEquals("idUser", foundUser.get().getUsername());
    }

    @Test
    void testFindById_ShouldReturnEmptyOptional_WhenIdDoesNotExist() {
        // Act
        Optional<User> foundUser = userRepository.findById(123L);

        // Assert
        assertFalse(foundUser.isPresent());
    }

}