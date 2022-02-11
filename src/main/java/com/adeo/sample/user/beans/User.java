package com.adeo.sample.user.beans;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.io.Serial;
import java.io.Serializable;

/**
 * Maps a user, immutable class.
 *
 * @param id
 *     User identifier.
 * @param firstName
 *     User first name.
 * @param lastName
 *     User last name.
 *
 * @author ccdpcloudops@adeo.com
 */
@JsonSerialize
@JsonIgnoreProperties(ignoreUnknown = true)
public record User(long id, String firstName, String lastName) implements Serializable {

    @Serial
    private static final long serialVersionUID = 5579813738085154303L;

    /**
     * Creates a {@link User} instance without identifier.
     *
     * @param firstName
     *     User first name.
     * @param lastName
     *     User last name.
     *
     * @return a {@link User} instance.
     */
    public static User notIdentified(final String firstName, final String lastName) {

        return new User(0L, firstName, lastName);
    }
}
