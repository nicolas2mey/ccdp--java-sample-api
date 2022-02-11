package com.adeo.sample.number.beans;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.io.Serial;
import java.io.Serializable;

/**
 * Maps a number API response.
 *
 * @param number
 *     Number value.
 * @param text
 *     Number description.
 *
 * @author ccdpcloudops@adeo.com
 */
@JsonSerialize
@JsonIgnoreProperties(ignoreUnknown = true)
public record NumberResponse(long number, String text) implements Serializable {

    @Serial
    private static final long serialVersionUID = -5442604544440334795L;

    /**
     * Creates a new {@link NumberResponse} instance.
     *
     * @param number
     *     number value.
     * @param text
     *     description.
     *
     * @return a {@link NumberResponse} instance.
     */
    public static NumberResponse from(final long number, final String text) {

        return new NumberResponse(number, text);
    }
}
