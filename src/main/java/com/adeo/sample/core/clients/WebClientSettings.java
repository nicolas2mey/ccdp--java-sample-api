package com.adeo.sample.core.clients;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Web clients settings mapping class.
 *
 * @author ccdpcloudops@adeo.com
 */
@Data
@AllArgsConstructor
@Slf4j
@SuppressWarnings("PMD.CommentSize")
public class WebClientSettings {

    private static final int DEFAULT_BUFFER_SIZE = -1;

    /**
     * Connection name, will be used to name associated pool.
     */
    private String name;

    /**
     * Web client base URl.
     */
    private String baseUrl;

    /**
     * Custom buffer size in kilo octets (example: 5 ko).
     */
    private int bufferSize;

    private ConnectionSettings connectionSettings;

    private PoolSettings poolSettings;

    /**
     * Creates a new {@link WebClientSettings} instance.
     */
    public WebClientSettings() {

        this.bufferSize = DEFAULT_BUFFER_SIZE;
        this.connectionSettings = ConnectionSettings.ofDefault();
        this.poolSettings = PoolSettings.ofDefault();
    }

    /**
     * Tells whether a custom buffer size must be defined for this client.
     *
     * @return {@code true} if a custom buffer size is required, {@code false} otherwise.
     */
    public boolean hasCustomBufferSize() {

        return DEFAULT_BUFFER_SIZE != this.bufferSize;
    }

    /**
     * Maps connection settings to perform web calls.
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ConnectionSettings {

        private static final int DEFAULT_CONNECT_TIMEOUT = 100;

        private static final int DEFAULT_READ_TIMEOUT = 5_000;

        private static final int DEFAULT_SOCKET_TIMEOUT = 2_500;

        private static final boolean DEFAULT_KEEP_ALIVE = true;

        private static final boolean DEFAULT_TCP_NO_DELAY = true;

        /**
         * TCP connection timeout.
         */
        private int connectTimeout;

        /**
         * TCP read timeout.
         */
        private int readTimeout;

        /**
         * TCP write timeout.
         */
        private int socketTimeout;

        /**
         * Tells whether connection must be reused (Keep-Alive).
         */
        private boolean soKeepAlive;

        /**
         * Enable/disable Nagle algorithm for successive small packets.
         */
        private boolean tcpNoDelay;

        /**
         * Creates a {@link ConnectionSettings} instance with default values.
         *
         * @return a {@link ConnectionSettings} instance.
         */
        public static ConnectionSettings ofDefault() {

            return ConnectionSettings.builder()
                                     .connectTimeout(DEFAULT_CONNECT_TIMEOUT)
                                     .readTimeout(DEFAULT_READ_TIMEOUT)
                                     .socketTimeout(DEFAULT_SOCKET_TIMEOUT)
                                     .soKeepAlive(DEFAULT_KEEP_ALIVE)
                                     .tcpNoDelay(DEFAULT_TCP_NO_DELAY)
                                     .build();
        }
    }

    /**
     * Maps connection pool settings.
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PoolSettings {

        private static final boolean DEFAULT_METRICS_ENABLED = true;

        private static final int DEFAULT_MAX_CONNECTIONS = 20;

        private static final int DEFAULT_PENDING_ACQUIRE_TIMEOUT = 2_000;

        private static final int DEFAULT_MAX_LIFE_TIME = -1;

        private static final int DEFAULT_MAX_IDLE_TIME = 1_000 * 60 * 60; // 1 hour

        private static final String DEFAULT_LEASING_STRATEGY = "lifo";

        private boolean metrics;

        private int maxConnections;

        private int pendingAcquireTimeout;

        private int maxLifeTime;

        private int maxIdleTime;

        private String leasingStrategy;

        /**
         * Creates a {@link PoolSettings} instance with default settings.
         * Default settings are:
         *
         * <ul>
         *     <li>metrics are NOT enabled</li>
         *     <li>max connections is set to 20</li>
         *     <li>pending acquire timeout is set to 2 000 milliseconds</li>
         *     <li>max connection life time is infinite</li>
         *     <li>max connection idle time is set to 1 hour</li>
         *     <li>leasing strategy is set to LIFO</li>
         * </ul>
         * <br>
         * <i>NB: this means the default waiting connection pool will be set to 40 (2 * max connections values).</i>
         * <br>
         * If you want to set a global leasing strategy for all created pools, you can set a JAVA property named
         * <code>reactor.netty.pool.leasingStrategy</code> with either:
         *  <ul>
         *      <li>lifo</li>
         *      <li>fifo</li>
         *  </ul>
         * <br>
         * To override default settings, add a <code>pool</code> section into your configuration file, i.e
         * <pre>
         *   api:
         *     name:
         *       base-url: http://localhost:1080/
         *       socket-timeout: 1000
         *       connect-timeout: 1000
         *       read-timeout: 2000
         *       so-keep-alive: true
         *       tcp-no-delay: true
         *       pool:
         *         metrics: false
         *         max-connections: 10
         *         pending-acquire-timeout: 1000
         *         max-life-time: 3600000
         *         max-idle-time: 3600000
         *         leasing-strategy: fifo
         * </pre>
         * <br>
         * <i>NB: you don't need to override every value, only the one you want them to differ from default ones.</i>
         *
         * @return a {@link PoolSettings} instance with default values.
         */
        public static PoolSettings ofDefault() {

            return PoolSettings.builder()
                               .metrics(DEFAULT_METRICS_ENABLED)
                               .maxConnections(DEFAULT_MAX_CONNECTIONS)
                               .pendingAcquireTimeout(DEFAULT_PENDING_ACQUIRE_TIMEOUT)
                               .maxLifeTime(DEFAULT_MAX_LIFE_TIME)
                               .maxIdleTime(DEFAULT_MAX_IDLE_TIME)
                               .leasingStrategy(DEFAULT_LEASING_STRATEGY)
                               .build();
        }
    }
}
