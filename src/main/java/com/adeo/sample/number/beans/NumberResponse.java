package com.adeo.sample.number.beans;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Maps a number API response.
 *
 * @author ccdpcloudops@adeo.com
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class NumberResponse implements Serializable {

    private static final long serialVersionUID = -5442604544440334795L;

    private long number;

    private String text;

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
