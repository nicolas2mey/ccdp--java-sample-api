package com.adeo.sample.warmup;

import com.adeo.sample.core.clients.WebClientFactory;
import com.adeo.sample.core.clients.WebClientSettings;
import com.adeo.sample.warmup.beans.WarmupConfig;
import com.adeo.sample.warmup.health.WarmupHealthIndicator;
import com.adeo.sample.warmup.runners.WarmupRunner;
import com.adeo.sample.warmup.services.WarmupService;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Warmup Spring configuration.
 *
 * @author ccdpcloudops@adeo.com
 */
@Configuration
@EnableConfigurationProperties
@NoArgsConstructor
public class WarmupConfiguration {

    private static final String WARMUP_WEB_CLIENT_SETTINGS = "warmupWebClientSettings";

    private static final String WARMUP_WEB_CLIENT = "warmupWebClient";

    private static final String WARMUP_HEALTH = "warmup";

    private static final String NOT_TEST_PROFILE = "!test";

    /**
     * Creates and returns warmup configuration bean.
     *
     * @return a {@link WarmupConfig} instance.
     */
    @Bean
    @ConfigurationProperties(prefix = "warmup")
    @Profile(NOT_TEST_PROFILE)
    public WarmupConfig getConfig() {

        return new WarmupConfig();
    }

    /**
     * Creates and return warmup web client settings.
     *
     * @param port
     *     application server port.
     *
     * @return a {@link WebClientSettings} instance.
     */
    @Bean(WARMUP_WEB_CLIENT_SETTINGS)
    @Profile(NOT_TEST_PROFILE)
    public WebClientSettings getWarmupWebClientSettings(@Value("${server.port:8080}") final int port) {

        final var settings = new WebClientSettings();

        settings.setName("warmup-client");
        settings.setBaseUrl(String.format("http://localhost:%d", port));
        settings.getPoolSettings().setMaxConnections(WarmupService.WARMUP_LOOP_SIZE * 3);

        return settings;
    }

    /**
     * Creates and return warmup web client.
     *
     * @param builder
     *     Spring {@link WebClient} builder.
     * @param settings
     *     warmup web client settings reference.
     *
     * @return a {@link WebClient} instance.
     */
    @Bean(WARMUP_WEB_CLIENT)
    @Profile(NOT_TEST_PROFILE)
    public WebClient getWarmupWebClient(final WebClient.Builder builder, @Qualifier(WARMUP_WEB_CLIENT_SETTINGS) final WebClientSettings settings) {

        return WebClientFactory.create(builder, settings);
    }

    /**
     * Creates and returns a warmup service instance.
     *
     * @param config
     *     warmup configuration bean reference.
     * @param webClient
     *     warmup web client reference.
     *
     * @return a {@link WarmupService} instance.
     */
    @Bean
    @Profile(NOT_TEST_PROFILE)
    public WarmupService getWarmupService(@Qualifier(WARMUP_WEB_CLIENT) final WebClient webClient, final WarmupConfig config) {

        return new WarmupService(webClient, config);
    }

    /**
     * Creates a warmup runner instance.
     *
     * @param warmupService
     *     warmup service reference.
     *
     * @return a {@link WarmupRunner} instance.
     */
    @Bean
    @Profile(NOT_TEST_PROFILE)
    public WarmupRunner getWarmupRunner(final WarmupService warmupService) {

        return new WarmupRunner(warmupService);
    }

    /**
     * Creates a custom warmup health indicator.
     *
     * @param warmupService
     *     warmup service reference.
     *
     * @return a {@link WarmupHealthIndicator} instance.
     */
    @Bean(WARMUP_HEALTH)
    @Profile(NOT_TEST_PROFILE)
    public WarmupHealthIndicator getWarmupHealthIndicator(final WarmupService warmupService) {

        return new WarmupHealthIndicator(warmupService);
    }
}
