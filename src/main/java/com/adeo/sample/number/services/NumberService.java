package com.adeo.sample.number.services;

import com.adeo.sample.number.beans.NumberResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * Number API service.
 *
 * @author ccdpcloudops@adeo.com
 */
@RequiredArgsConstructor
@Slf4j
public class NumberService {

    private static final String RANDOM_NUMBER_URI = "/random/trivia?json";

    private static final String RESILIENCE_NAME = "number-api";

    private static final Mono<NumberResponse> DEFAULT_RESPONSE = Mono.just(
        NumberResponse.from(42L, "Default magic number when service is down.")
    );

    private final WebClient numberApiClient;

    /**
     * Returns a new random number with its description.
     *
     * @return a {@link Mono} of {@link NumberResponse}.
     */
    @CircuitBreaker(name = RESILIENCE_NAME,
                    fallbackMethod = "fallback")
    @TimeLimiter(name = RESILIENCE_NAME)
    public Mono<NumberResponse> nextRandom() {

        return this.numberApiClient.get()
                                   .uri(RANDOM_NUMBER_URI)
                                   .retrieve()
                                   .bodyToMono(NumberResponse.class)
                                   .doOnError(throwable -> log.error("Could not retrieve a random number: ", throwable));
    }

    @SuppressWarnings("PMD.UnusedPrivateMethod")
    private Mono<NumberResponse> fallback(final RuntimeException exception) {

        log.error("An error occurred while trying to get a new number: ", exception);

        return DEFAULT_RESPONSE;
    }
}
