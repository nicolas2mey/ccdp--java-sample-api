package com.adeo.sample.core.clients;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

/**
 * {@link WebClient} factory class.
 *
 * @author ccdpcloudops@adeo.com.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Slf4j
public final class WebClientFactory {

    /**
     * Creates a {@link WebClient} instance.
     *
     * @param builder
     *     Spring web client builder reference.
     * @param settings
     *     web client settings reference.
     *
     * @return a {@link WebClient} instance.
     */
    public static WebClient create(final WebClient.Builder builder, final WebClientSettings settings) {

        log.info("Creating WebClient for settings: {}", settings);

        final HttpClient httpClient = HttpClient.create(
                                                    ConnectionProvider.builder(settings.getName())
                                                                      .maxConnections(settings.getPoolSettings().getMaxConnections())
                                                                      .maxIdleTime(Duration.ofMillis(settings.getPoolSettings().getMaxIdleTime()))
                                                                      .maxLifeTime(Duration.ofMillis(settings.getPoolSettings().getMaxLifeTime()))
                                                                      .pendingAcquireTimeout(Duration.ofMillis(settings.getPoolSettings().getPendingAcquireTimeout()))
                                                                      .metrics(settings.getPoolSettings().isMetrics())
                                                                      .build()
                                                )
                                                .option(ChannelOption.SO_KEEPALIVE, settings.getConnectionSettings().isSoKeepAlive())
                                                .option(ChannelOption.TCP_NODELAY, settings.getConnectionSettings().isTcpNoDelay())
                                                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, settings.getConnectionSettings().getConnectTimeout())
                                                .responseTimeout(Duration.ofMillis(settings.getConnectionSettings().getReadTimeout()))
                                                .doOnConnected(conn -> conn.addHandlerLast(
                                                                               new ReadTimeoutHandler(
                                                                                   settings.getConnectionSettings().getReadTimeout(), TimeUnit.MILLISECONDS)
                                                                           )
                                                                           .addHandlerLast(
                                                                               new WriteTimeoutHandler(
                                                                                   settings.getConnectionSettings().getSocketTimeout(), TimeUnit.MILLISECONDS)
                                                                           )
                                                );

        WebClient.Builder intermediate = builder.clientConnector(new ReactorClientHttpConnector(httpClient));

        if (settings.hasCustomBufferSize()) {
            intermediate = intermediate.codecs(
                configurer -> configurer.defaultCodecs()
                                        .maxInMemorySize(1024 * 1024 * settings.getBufferSize())
            );
        }

        return intermediate.baseUrl(settings.getBaseUrl())
                           .build();
    }
}
