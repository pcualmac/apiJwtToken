package com.example.apiJwtToken.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@EnableConfigurationProperties(AppSpringProperties.class)
@TestPropertySource(locations = "classpath:application.properties")  // Load the correct file
class AppSpringPropertiesTest {

    @Autowired
    private AppSpringProperties appSpringProperties;

    @Test
    void debugSpringProperties() {
        assertThat(appSpringProperties.getName()).isEqualTo("apiJwtToken");
        assertThat(appSpringProperties.getDatasourceUsername()).isEqualTo("root");
        assertThat(appSpringProperties.getDatasourcePassword()).isEqualTo("rootpassword");
        assertThat(appSpringProperties.getSecurityUserName()).isEqualTo("your_username");
        assertThat(appSpringProperties.getSecurityUserPassword()).isEqualTo("your_password");
    }
    
}
