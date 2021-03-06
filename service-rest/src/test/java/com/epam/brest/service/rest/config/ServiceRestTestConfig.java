package com.epam.brest.service.rest.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@TestConfiguration
public class ServiceRestTestConfig {
    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
