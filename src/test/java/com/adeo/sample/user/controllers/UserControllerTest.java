package com.adeo.sample.user.controllers;

import com.adeo.sample.TestContext;
import com.adeo.sample.core.beans.ErrorResource;
import com.adeo.sample.user.beans.User;
import com.adeo.sample.user.factories.UserFactory;
import com.adeo.sample.user.services.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.hamcrest.CoreMatchers.equalTo;

@DisplayName("User controller test case.")
@WebFluxTest(UserController.class)
@Import(TestContext.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserControllerTest {

    private static final User TEST_USER = new User(1L, "Foo", "Bar");

    @Autowired
    WebTestClient webTestClient;

    /*
     * NB: notice here that as the user service is a sample in memory "database", it is not mocked.
     * A more realistic application that would use a real database would require mocking artifacts.
     */
    @Autowired
    UserService userService;

    @Test
    @DisplayName("Ensures adding user works accurately.")
    @Order(1)
    void ensures_adding_user_works_accurately() {

        this.webTestClient.post()
                          .uri("/users")
                          .contentType(MediaType.APPLICATION_JSON)
                          .accept(MediaType.APPLICATION_JSON)
                          .body(
                              Mono.just(User.notIdentified("Foo", "Bar")),
                              User.class
                          )
                          .exchange()
                          .expectStatus()
                          .isOk()
                          .expectBody(User.class)
                          .value(user -> user, equalTo(TEST_USER));

    }

    @Test
    @DisplayName("Ensures get existing user works accurately.")
    @Order(2)
    void ensures_get_existing_user_works_accurately() {

        this.webTestClient.get()
                          .uri("/users/1")
                          .accept(MediaType.APPLICATION_JSON)
                          .exchange()
                          .expectStatus()
                          .isOk()
                          .expectBody(User.class)
                          .value(user -> user, equalTo(TEST_USER));
    }

    @Test
    @DisplayName("Ensures get non existing user works accurately.")
    @Order(3)
    void ensures_get_non_existing_user_works_accurately() {

        this.webTestClient.get()
                          .uri("/users/2")
                          .accept(MediaType.APPLICATION_JSON)
                          .exchange()
                          .expectStatus()
                          .value(equalTo(HttpStatus.NOT_FOUND.value()))
                          .expectBody(ErrorResource.class);
    }

    @Test
    @DisplayName("Ensures list all users works accurately.")
    @Order(4)
    void ensures_list_all_users_works_accurately() {

        // Adds a second user using service directly, so we must force subscription.
        this.userService.add(UserFactory.create("Foo 2", "Bar 2"))
                        .subscribe();

        this.webTestClient.get()
                          .uri("/users")
                          .accept(MediaType.APPLICATION_JSON)
                          .exchange()
                          .expectStatus()
                          .isOk()
                          .expectBodyList(User.class)
                          .hasSize(2);
    }

    @Test
    @DisplayName("Ensures deleting user works accurately.")
    @Order(5)
    void ensures_deleting_user_works_accurately() {

        this.webTestClient.delete()
                          .uri("/users/1")
                          .accept(MediaType.APPLICATION_JSON)
                          .exchange()
                          .expectStatus()
                          .isOk()
                          .expectBody(User.class)
                          .value(user -> user, equalTo(TEST_USER));

        this.webTestClient.get()
                          .uri("/users/1")
                          .accept(MediaType.APPLICATION_JSON)
                          .exchange()
                          .expectStatus()
                          .value(equalTo(HttpStatus.NOT_FOUND.value()))
                          .expectBody(ErrorResource.class);
    }

    @Test
    @DisplayName("Ensures deleting non existing user works accurately.")
    @Order(6)
    void ensures_deleting_non_existing_user_works_accurately() {

        this.webTestClient.delete()
                          .uri("/users/3")
                          .accept(MediaType.APPLICATION_JSON)
                          .exchange()
                          .expectStatus()
                          .value(equalTo(HttpStatus.NOT_FOUND.value()))
                          .expectBody(ErrorResource.class);
    }
}
