package com.adeo.sample.core.exceptions;

import lombok.NonNull;

/**
 * Custom exception for not found resources.
 *
 * @author ccdpcloudops@adeo.com
 */
public final class ResourceNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 7566646510087693533L;

    private ResourceNotFoundException(final String message) {
        super(message);
    }

    /**
     * Creates a new {@link ResourceNotFoundException} instance with provided message.
     *
     * @param message
     *     error message.
     *
     * @return a {@link ResourceNotFoundException} instance.
     */
    public static ResourceNotFoundException withMessage(@NonNull final String message) {

        return new ResourceNotFoundException(message);
    }
}
