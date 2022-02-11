package com.adeo.sample.warmup.services;

import com.adeo.sample.warmup.beans.WarmupConfig;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.stream.Stream;

/**
 * Warmup service.
 *
 * @author ccdpcloudops@adeo.com.
 */
@RequiredArgsConstructor
@Slf4j
public class WarmupService {

    /**
     * Warmup loop length (i.e. number of times all URI will be called).
     */
    public static final int WARMUP_LOOP_SIZE = 10;

    private final WebClient warmupClient;

    private final WarmupConfig config;

    @Getter
    private boolean finished;

    /**
     * Runs warmup service.
     *
     * @return a {@link Mono} of {@link Void}.
     */
    public Mono<Void> run() {

        this.finished = false;

        return Flux.fromStream(this.duplicateUris())
                   .flatMap(
                       uri -> this.warmupClient.get()
                                               .uri(uri)
                                               .retrieve()
                                               .bodyToMono(String.class)
                                               .doOnSubscribe(subscription -> log.debug("Calling URI {}...", uri))
                   )
                   .then()
                   .doOnSubscribe(subscription -> log.debug("Warmup is starting."))
                   .doOnTerminate(() -> {
                       this.finished = true;

                       log.debug("Warmup is finished");
                   });
    }

    private Stream<String> duplicateUris() {

        return Stream.iterate(this.config.uris(), candidate -> candidate)
                     .limit(WARMUP_LOOP_SIZE)
                     .reduce(new ArrayList<>(), (lst1, lst2) -> {
                         lst1.addAll(lst2);

                         return lst1;
                     })
                     .stream();
    }
}
