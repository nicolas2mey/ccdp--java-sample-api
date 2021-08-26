package com.adeo.sample.core.beans;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.io.Serializable;

/**
 * Basic bean for error management as a JSON response.
 *
 * @author ccdpcloudops@adeo.com
 */
@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
public class ErrorResource implements Serializable {

    private static final long serialVersionUID = -8684011500459048592L;

    private String message;

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
