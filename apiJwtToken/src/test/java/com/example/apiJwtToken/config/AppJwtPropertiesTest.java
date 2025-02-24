package com.example.apiJwtToken.config;

import org.junit.jupiter.api.Test;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = AppJwtProperties.class)
@EnableConfigurationProperties(AppJwtProperties.class)
class AppJwtPropertiesTest {

    @Autowired
    private AppJwtProperties appJwtProperties;


    @Test
    void propertiesShouldBeLoaded() {
        assertThat(appJwtProperties.getSecret()).isEqualTo("UT+NxI9nWRKsi8oMkEoVLFeXtUUaJKhdrELMqxZZt0fmRM3/memVVmlTXRlBfDGDFd4/BUu2CfbzFhBb0t0MZg==");
        assertThat(appJwtProperties.getExpirationMs()).isEqualTo("3600000");
    }
}
