package com.adeo.sample.user.factories;

import com.adeo.sample.user.beans.User;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Factory dedicated to {@link User} creation.
 *
 * @author ccdpcloudops@adeo.com
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class UserFactory {

    private static final long INITIAL_COUNTER_VALUE = 0L;

    private static final AtomicLong USER_CREATED_COUNTER = new AtomicLong(INITIAL_COUNTER_VALUE);

    /**
     * Resets users created counter to initial value.<br>
     * !Tests usage only !
     */
    static /* default */ void reset() {
        USER_CREATED_COUNTER.set(INITIAL_COUNTER_VALUE);
    }

    /**
     * Creates a new {@link User} with provided first name and last name.
     *
     * @param firstName
     *     user's first name.
     * @param lastName
     *     user's last name.
     *
     * @return A {@link User} instance.
     *
     * @throws IllegalArgumentException
     *     if provided {@code firstName} or {@code lastName} is {@code null} or empty.
     */
    public static User create(@Nullable final String firstName, @Nullable final String lastName) {

        Assert.hasText(firstName, () -> "User's first name cannot be null or empty !");
        Assert.hasText(lastName, () -> "User's last name cannot be null or empty !");

        return User.builder()
                   .id(USER_CREATED_COUNTER.incrementAndGet())
                   .firstName(firstName)
                   .lastName(lastName)
                   .build();
    }
}
