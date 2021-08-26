package com.adeo.sample;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Tiny test configuration class used for tests who use @WebFluxTest annotation, to provide some
 * required beans.
 */
@Configuration
@ActiveProfiles("test")
public class TestContext {

    @Bean
    public WebClient.Builder getBuilder() {

        return WebClient.builder();
    }
}
