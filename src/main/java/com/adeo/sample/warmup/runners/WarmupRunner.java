package com.adeo.sample.warmup.runners;

import com.adeo.sample.warmup.services.WarmupService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;

/**
 * Warmup runner class.
 *
 * @author ccdpcloudops@adeo.com.
 */
@RequiredArgsConstructor
public class WarmupRunner implements CommandLineRunner {

    private final WarmupService warmupService;

    @Override
    public void run(final String... args) throws Exception {

        this.warmupService.run()
                          .subscribe();
    }
}
