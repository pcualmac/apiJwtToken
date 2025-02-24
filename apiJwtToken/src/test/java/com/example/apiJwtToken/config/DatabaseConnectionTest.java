package com.example.apiJwtToken.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class DatabaseConnectionTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    public void setup() {
        // Create the schema 'TESTDB' if it doesn't exist
        jdbcTemplate.execute("CREATE SCHEMA IF NOT EXISTS TESTDB");
    }

    @Test
    public void testDatabaseConnection() {
        // Ensure that the JdbcTemplate is properly autowired and the database connection is available
        assertTrue(jdbcTemplate != null, "JdbcTemplate should not be null");

        // Check if the 'TESTDB' schema exists in the H2 database
        Boolean schemaExists = jdbcTemplate.queryForObject(
            "SELECT COUNT(*) FROM INFORMATION_SCHEMA.SCHEMATA WHERE SCHEMA_NAME = 'TESTDB'", 
            Boolean.class
        );
        assertTrue(schemaExists != null && schemaExists, "Schema 'TESTDB' should exist");

        Boolean databaseExists = jdbcTemplate.queryForObject(
            "SELECT COUNT(*) FROM INFORMATION_SCHEMA.SCHEMATA WHERE SCHEMA_NAME = 'TESTDB'", 
            Boolean.class
        );
        assertTrue(databaseExists != null && databaseExists, "Database 'TESTDB' should exist");
    }
}
