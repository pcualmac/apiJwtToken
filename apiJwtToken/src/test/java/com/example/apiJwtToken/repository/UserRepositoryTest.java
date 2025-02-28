package com.example.apiJwtToken.repository;

import com.example.apiJwtToken.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@TestPropertySource(locations = "classpath:application-test.properties")
@Sql(scripts = "classpath:schema.sql", executionPhase = ExecutionPhase.BEFORE_TEST_CLASS)
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setUsername("testUser");
        testUser.setPassword("password");
        userRepository.save(testUser);
    }

    @Test
    void findByUsername_ShouldReturnUser_WhenUsernameExists() {
        Optional<User> foundUser = userRepository.findByUsername("testUser");
        assertTrue(foundUser.isPresent());
        assertEquals("testUser", foundUser.get().getUsername());
    }

    @Test
    void findByUsername_ShouldReturnEmpty_WhenUsernameDoesNotExist() {
        Optional<User> foundUser = userRepository.findByUsername("nonExistentUser");
        assertFalse(foundUser.isPresent());
    }

    @Test
    void save_ShouldPersistUser_WhenUserIsValid() {
        User newUser = new User();
        newUser.setUsername("newUser");
        newUser.setPassword("newPassword");

        User savedUser = userRepository.save(newUser);

        assertNotNull(savedUser.getId());
        assertEquals("newUser", savedUser.getUsername());
    }

    @Test
    void findAll_ShouldReturnAllUsers() {
        User anotherUser = new User();
        anotherUser.setUsername("anotherUser");
        anotherUser.setPassword("anotherPassword");
        userRepository.save(anotherUser);

        List<User> users = userRepository.findAll();
        assertEquals(2, users.size());
        assertTrue(users.stream().anyMatch(user -> user.getUsername().equals("testUser")));
        assertTrue(users.stream().anyMatch(user -> user.getUsername().equals("anotherUser")));
    }

    @Test
    void findById_ShouldReturnUser_WhenIdExists() {
        Optional<User> foundUser = userRepository.findById(testUser.getId());
        assertTrue(foundUser.isPresent());
        assertEquals("testUser", foundUser.get().getUsername());
    }

    @Test
    void findById_ShouldReturnEmpty_WhenIdDoesNotExist() {
        Optional<User> foundUser = userRepository.findById(999L);
        assertFalse(foundUser.isPresent());
    }

    @Test
    void deleteById_ShouldRemoveUser_WhenIdExists() {
        userRepository.deleteById(testUser.getId());
        assertFalse(userRepository.findById(testUser.getId()).isPresent());
    }

    @Test
    void delete_ShouldRemoveUser_WhenUserExists() {
        User userToDelete = new User();
        userToDelete.setUsername("deleteMe");
        userToDelete.setPassword("deleteMePassword");
        userRepository.save(userToDelete);

        userRepository.delete(userToDelete);

        assertFalse(userRepository.findByUsername("deleteMe").isPresent());
    }

    @Test
    void existsById_ShouldReturnTrue_WhenIdExists() {
        assertTrue(userRepository.existsById(testUser.getId()));
    }

    @Test
    void existsById_ShouldReturnFalse_WhenIdDoesNotExist() {
        assertFalse(userRepository.existsById(999L));
    }
}