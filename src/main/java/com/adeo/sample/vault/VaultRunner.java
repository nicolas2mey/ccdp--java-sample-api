package com.adeo.sample.vault;

import com.adeo.sample.core.logging.LogUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/**
 * Dumb command line runner to display a not real Vault secret.
 *
 * @author ccdpcloudops@adeo.com.
 */
@Component
@Profile("!test")
@Slf4j
public class VaultRunner implements CommandLineRunner {

    private final String applicationKey;

    /**
     * Creates a new Vault runner instance.
     *
     * @param applicationKey
     *     application key that will be injected by Spring.
     */
    public VaultRunner(@Value("${application.key:-}") final String applicationKey) {
        this.applicationKey = applicationKey;
    }

    @Override
    public void run(final String... args) throws Exception {

        LogUtils.secureLog(
            () -> log.info("Hey, I've got a secret for you: {} !", this.applicationKey)
        );
    }
}
