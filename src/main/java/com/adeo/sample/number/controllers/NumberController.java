package com.adeo.sample.number.controllers;

import com.adeo.sample.number.beans.NumberResponse;
import com.adeo.sample.number.services.NumberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * Number REST controller.
 *
 * @author ccdpcloudops@adeo.com.
 */
@RestController
@RequestMapping("numbers")
@RequiredArgsConstructor
public class NumberController {

    private final NumberService numberService;

    /**
     * Returns a new random number response.
     *
     * @return a {@link Mono} of {@link NumberResponse}.
     */
    @GetMapping(value = "/random",
                produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<NumberResponse> next() {

        return this.numberService.nextRandom();
    }
}
