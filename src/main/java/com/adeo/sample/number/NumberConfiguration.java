package com.adeo.sample.number;

import com.adeo.sample.core.clients.WebClientFactory;
import com.adeo.sample.core.clients.WebClientSettings;
import com.adeo.sample.number.controllers.NumberController;
import com.adeo.sample.number.services.NumberService;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Number API Spring configuration class.
 *
 * @author ccdpcloudops@adeo.com
 */
@Configuration
@ComponentScan(basePackageClasses = NumberController.class)
@EnableConfigurationProperties
@NoArgsConstructor
public class NumberConfiguration {

    private static final String NUMBER_WEB_CLIENT_SETTINGS = "numberApiSettings";

    private static final String NUMBER_WEB_CLIENT = "numberApiClient";

    /**
     * Creates and returns number API web client settings.
     *
     * @return a {@link WebClientSettings} instance.
     */
    @Bean(NUMBER_WEB_CLIENT_SETTINGS)
    @ConfigurationProperties(prefix = "apis.number")
    public WebClientSettings getWebClientSettings() {

        return new WebClientSettings();
    }

    /**
     * Creates and returns number API {@link WebClient} instance.
     *
     * @param builder
     *     Spring web client builder reference.
     * @param settings
     *     web client settings reference.
     *
     * @return a {@link WebClient} instance.
     */
    @Bean(NUMBER_WEB_CLIENT)
    public WebClient getNumberApiClient(final WebClient.Builder builder, @Qualifier(NUMBER_WEB_CLIENT_SETTINGS) final WebClientSettings settings) {

        return WebClientFactory.create(builder, settings);
    }

    /**
     * Creates and returns a number service instance.
     *
     * @param numberApiClient
     *     {@link WebClient} instance to use.
     *
     * @return a {@link NumberService} instance.
     */
    @Bean
    public NumberService getNumberService(@Qualifier(NUMBER_WEB_CLIENT) final WebClient numberApiClient) {

        return new NumberService(numberApiClient);
    }
}
