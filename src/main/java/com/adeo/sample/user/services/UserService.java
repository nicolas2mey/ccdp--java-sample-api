package com.adeo.sample.user.services;

import com.adeo.sample.user.beans.User;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * {@link User} service management, i.e. retrieves, adds, deletes {@link User}.
 * Here, a simple in memory Map storage will be used instead of a real permanent storage.
 * ! This is for sample usage only !
 *
 * @author ccdpcloudops@adeo.com
 */
@Slf4j
public class UserService {

    private final Map<Long, User> users;

    /**
     * Creates a new {@link UserService} instance.
     */
    public UserService() {
        this.users = new ConcurrentHashMap<>();
    }

    /**
     * Empties current service user list.
     */
    public void clear() {

        this.users.clear();
    }

    /**
     * Returns all known users.
     *
     * @return a {@link Flux} of {@link User}.
     */
    public Flux<User> allUsers() {

        return Flux.fromIterable(this.users.values());
    }

    /**
     * Retries a {@link User} through its identifier.
     *
     * @param id
     *     expected {@link User} identifier.
     *
     * @return a {@link Mono} of {@link User}, {@link Mono#empty()} if no user matches.
     */
    public Mono<User> findById(final long id) {

        return Mono.justOrEmpty(this.users.get(id))
                   .doOnSubscribe(subscription -> log.debug("Searching for User with ID {}", id));
    }

    /**
     * Adds a {@link User} to current service instance.
     * If provided {@code user} has an invalid identifier (i.e. &lt;= 0), it will not be added.
     * <br>
     * {@link User} beans should be created using {@link com.adeo.sample.user.factories.UserFactory} class.
     *
     * @param user
     *     {@link User} bean instance to add.
     *
     * @return added {@link User} reference.
     */
    public Mono<User> add(final User user) {

        return Mono.just(user)
                   .filter(candidate -> 0L != candidate.id())
                   .filter(candidate -> !this.users.containsKey(candidate.id()))
                   .doOnNext(candidate -> this.users.put(candidate.id(), candidate))
                   .doOnSubscribe(subscription -> log.debug("Trying to add user {}/{}", user.firstName(), user.lastName()));
    }

    /**
     * Deletes {@link User} with provided identifier from the current service.
     *
     * @param id
     *     user's to delete identifier.
     *
     * @return a {@link Mono} of the deleted {@link User}, {@link Mono#empty()} if no user matches this {@code id}.
     */
    public Mono<User> delete(final long id) {

        return Mono.justOrEmpty(this.users.remove(id))
                   .doOnSubscribe(subscription -> log.debug("Trying ot delete User with ID {}", id));
    }
}
