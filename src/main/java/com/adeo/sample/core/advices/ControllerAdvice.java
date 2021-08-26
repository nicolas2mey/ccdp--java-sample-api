package com.adeo.sample.core.advices;

import com.adeo.sample.core.beans.ErrorResource;
import com.adeo.sample.core.exceptions.ResourceNotFoundException;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reactor.core.publisher.Mono;

import java.util.concurrent.TimeoutException;

/**
 * Spring controller advice for REST API management.<br>
 * This advice is made for response in JSON format.
 *
 * @author ccdpcloudops@adeo.com.
 */
@RestControllerAdvice
@NoArgsConstructor
public class ControllerAdvice {

    /**
     * Deals with 404 errors.
     *
     * @param exception
     *     {@link ResourceNotFoundException} instance that will cause interception.
     *
     * @return a {@link Mono} of a {@link ResponseEntity}.
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public Mono<ResponseEntity<ErrorResource>> notFound(final ResourceNotFoundException exception) {

        return this.handleError(exception, HttpStatus.NOT_FOUND);
    }

    /**
     * Deals with client calls timeout.
     *
     * @param exception
     *     {@link TimeoutException} that raised the exception.
     *
     * @return a {@link Mono} of a {@link ResponseEntity}.
     */
    @ExceptionHandler(TimeoutException.class)
    public Mono<ResponseEntity<ErrorResource>> proxyTimeOut(final TimeoutException exception) {

        return this.handleError(exception, HttpStatus.GATEWAY_TIMEOUT);
    }

    /**
     * Deals with all other errors.
     *
     * @param exception
     *     {@link Exception} instance that will cause interception.
     *
     * @return a {@link Mono} of a {@link ResponseEntity}.
     */
    @ExceptionHandler(Exception.class)
    public Mono<ResponseEntity<ErrorResource>> internalServerError(final Exception exception) {

        return this.handleError(exception, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private Mono<ResponseEntity<ErrorResource>> handleError(final Exception exception, final HttpStatus status) {

        return Mono.just(ErrorResource.withMessage(exception.getMessage()))
                   .map(error -> new ResponseEntity<>(error, status));
    }
}
