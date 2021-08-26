package com.adeo.sample.user.beans;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Value;

import java.io.Serializable;

/**
 * Maps a user, immutable class.
 *
 * @author ccdpcloudops@adeo.com
 */
@Value
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class User implements Serializable {

    private static final long serialVersionUID = 5579813738085154303L;

    long id;

    String firstName;

    String lastName;
}
