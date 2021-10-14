package com.adeo.sample.core;

import com.adeo.sample.core.services.ThreadService;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Core configuration Spring module definition.
 *
 * @author ccdccloudops@adeo.com
 */
@Configuration
@ComponentScan(basePackageClasses = RestControllerAdvice.class)
@NoArgsConstructor
public class CoreConfiguration {

    /**
     * Creates and exposes {@link ThreadService} instance.
     *
     * @return a {@link ThreadService} instance.
     */
    @Bean
    public ThreadService getThreadService() {
        return new ThreadService();
    }
}
