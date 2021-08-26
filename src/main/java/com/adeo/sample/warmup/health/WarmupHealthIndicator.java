package com.adeo.sample.warmup.health;

import com.adeo.sample.warmup.services.WarmupService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;

/**
 * Creates a custom health indicator.
 *
 * @author ccdpcloudops@adeo.com.
 */
@RequiredArgsConstructor
public class WarmupHealthIndicator implements HealthIndicator {

    private final WarmupService warmupService;

    @Override
    public Health health() {

        return (this.warmupService.isFinished()
            ? Health.up()
            : Health.down()
        ).build();
    }
}
