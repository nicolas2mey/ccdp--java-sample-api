package com.adeo.sample.user.controllers;

import com.adeo.sample.core.exceptions.ResourceNotFoundException;
import com.adeo.sample.user.beans.User;
import com.adeo.sample.user.factories.UserFactory;
import com.adeo.sample.user.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * User REST endpoint.
 *
 * @author ccdpcloudops@adeo.com
 */
@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * Returns all users as a list.
     *
     * @return all {@link User} as a list.
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<User> allUsers() {

        return this.userService.allUsers();
    }

    /**
     * Retrieves a {@link User} through its identifier.
     *
     * @param id
     *     user's identifier.
     *
     * @return matching {@link User}.
     */
    @GetMapping(value = "/{id}",
                produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<User> findById(@PathVariable final long id) {

        return this.userService.findById(id)
                               .switchIfEmpty(
                                   Mono.error(
                                       ResourceNotFoundException.withMessage(
                                           String.format("User with id `%d` does not exists.", id)
                                       )
                                   )
                               );
    }

    /**
     * Adds a new {@link User}.
     *
     * @param candidate
     *     user to add.
     *
     * @return created {@link User} data.
     */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
                 produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<User> add(@RequestBody final User candidate) {

        return this.userService.add(UserFactory.create(candidate.getFirstName(), candidate.getLastName()))
                               .switchIfEmpty(
                                   Mono.error(
                                       new IllegalArgumentException(
                                           String.format("Cannot create user for input: %s", candidate)
                                       )
                                   )
                               );
    }

    /**
     * Deletes user with provided identifier.
     *
     * @param id
     *     user to delete identifier.
     *
     * @return deleted {@link User} data.
     */
    @DeleteMapping(value = "/{id}",
                   produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<User> delete(@PathVariable final long id) {

        return this.findById(id)
                   .flatMap(toDelete -> this.userService.delete(toDelete.getId()))
                   .switchIfEmpty(
                       Mono.error(
                           new IllegalArgumentException(
                               String.format("Could not delete user with id `%s`", id)
                           )
                       )
                   );
    }
}
