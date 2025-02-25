package com.example.apiJwtToken;

import com.example.apiJwtToken.config.AppJwtProperties;
import com.example.apiJwtToken.config.AppSpringProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({AppSpringProperties.class, AppJwtProperties.class}) // Add this line
public class ApiJwtTokenApplication {
    public static void main(String[] args) {
        SpringApplication.run(ApiJwtTokenApplication.class, args);
    }
}