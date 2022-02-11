package com.adeo.sample.core.beans;

import lombok.NonNull;

import java.io.Serial;
import java.io.Serializable;

/**
 * Basic bean for error management as a JSON response.
 *
 * @param message
 *     error message to display.
 *
 * @author ccdpcloudops@adeo.com
 */
public record ErrorResource(String message) implements Serializable {

    @Serial
    private static final long serialVersionUID = -8684011500459048592L;

    /**
     * Creates a new {@link ErrorResource} instance.
     *
     * @param message
     *     error message to display.
     *
     * @return en {@link ErrorResource} instance.
     *
     * @throws NullPointerException
     *     if provided {@code message} is {@code null}.
     */
    public static ErrorResource withMessage(@NonNull final String message) {

        return new ErrorResource(message);
    }
}
