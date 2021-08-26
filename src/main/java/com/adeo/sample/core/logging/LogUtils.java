package com.adeo.sample.core.logging;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.slf4j.MDC;

/**
 * Logging utility class.
 *
 * @author ccdpcloupops@adeo.com.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class LogUtils {

    private static final String MDC_PRIVATE_FIELD = "private";

    private static final String PRIVACY_ENABLED = "1";

    /**
     * Logs private data.<br>
     * <br>
     * Example:
     *
     * <pre>{@code
     * LogUtils.secureLog(
     *      () -> log.info("My log with secret data: {}", mySecret)
     * );}</pre>
     *
     * @param logAction
     *     log action to perform.
     */
    public static void secureLog(final Runnable logAction) {

        try (var mdc = MDC.putCloseable(MDC_PRIVATE_FIELD, PRIVACY_ENABLED)) {
            logAction.run();
        }
    }
}
