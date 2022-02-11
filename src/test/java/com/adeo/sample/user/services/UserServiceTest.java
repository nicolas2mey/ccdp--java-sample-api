package com.adeo.sample.user.services;

import com.adeo.sample.user.beans.User;
import com.adeo.sample.user.factories.UserFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.concurrent.atomic.AtomicLong;

@DisplayName("User service test case.")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserServiceTest {

    private final static UserService SUBJECT = new UserService();

    private final static AtomicLong LAST_CREATED_ID = new AtomicLong(0L);

    private static final String DEFAULT_FIRST_NAME = "Foo";

    private static final String DEFAULT_LAST_NAME = "Bar";

    @BeforeAll
    static void setUp() {
        SUBJECT.clear();
    }

    @Test
    @DisplayName("Ensures creating new user works accurately.")
    @Order(1)
    void ensures_creating_new_user_works_accurately() {

        StepVerifier.create(
                        SUBJECT.add(UserFactory.create(DEFAULT_FIRST_NAME, DEFAULT_LAST_NAME))
                               .doOnNext(created -> LAST_CREATED_ID.set(created.id()))
                    )
                    .expectNextMatches(
                        user -> DEFAULT_FIRST_NAME.equalsIgnoreCase(user.firstName())
                            && DEFAULT_LAST_NAME.equalsIgnoreCase(user.lastName())
                    )
                    .verifyComplete();
    }

    @Test
    @DisplayName("Ensures adding user with invalid identifier fails.")
    @Order(2)
    void ensures_adding_user_with_invalid_id_fails() {

        StepVerifier.create(SUBJECT.add(User.notIdentified(DEFAULT_FIRST_NAME, DEFAULT_LAST_NAME)))
                    .expectNextCount(0L)
                    .verifyComplete();
    }

    @Test
    @DisplayName("Ensures finding existing user by its identifier works accurately.")
    @Order(3)
    void ensures_finding_existing_user_by_id_works_accurately() {

        StepVerifier.create(SUBJECT.findById(LAST_CREATED_ID.get()))
                    .expectNextMatches(
                        user -> DEFAULT_FIRST_NAME.equalsIgnoreCase(user.firstName())
                            && DEFAULT_LAST_NAME.equalsIgnoreCase(user.lastName()))
                    .verifyComplete();
    }

    @Test
    @DisplayName("Ensures adding new user and list works accurately.")
    @Order(4)
    void ensures_adding_new_user_and_list_works_accurately() {

        StepVerifier.create(
                        SUBJECT.add(UserFactory.create("Foo2", "Bar2"))
                               .thenMany(SUBJECT.allUsers())
                    )
                    .expectNextCount(2L)
                    .verifyComplete();
    }

    @Test
    @DisplayName("Ensures delete user works accurately.")
    @Order(5)
    void ensures_delete_user_works_accurately() {

        StepVerifier.create(
                        Mono.just(LAST_CREATED_ID.get())
                            .flatMap(SUBJECT::delete)
                            .thenMany(SUBJECT.allUsers())
                    )
                    .expectNextCount(1L)
                    .verifyComplete();
    }

    @Test
    @DisplayName("Ensures deleting non existing user works accurately.")
    @Order(6)
    void ensures_deleting_non_existing_user_works_accurately() {

        StepVerifier.create(SUBJECT.delete(999L))
                    .expectNextCount(0L)
                    .verifyComplete();
    }

    @Test
    @DisplayName("Ensures clear method works accurately.")
    @Order(7)
    void ensures_clear_method_works_accurately() {

        StepVerifier.create(
                        Mono.fromSupplier(() -> {
                            SUBJECT.clear();

                            return "List all clear";
                        }).thenMany(SUBJECT.allUsers())
                    )
                    .expectNextCount(0L)
                    .verifyComplete();
    }
}
