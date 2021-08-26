package com.adeo.sample.number.controllers;

import com.adeo.sample.TestContext;
import com.adeo.sample.number.beans.NumberResponse;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.equalTo;

@DisplayName("Number controller test case.")
@WebFluxTest(NumberController.class)
@Import(TestContext.class)
class NumberControllerTest {

    private static MockWebServer mockedNumberApiServer;

    @Autowired
    WebTestClient webTestClient;

    @BeforeAll
    static void setUp() throws IOException {
        mockedNumberApiServer = new MockWebServer();
        mockedNumberApiServer.start(9080);
    }

    @AfterAll
    static void tearDown() throws IOException {
        mockedNumberApiServer.shutdown();
    }

    @Test
    @DisplayName("Ensures getting random number works accurately.")
    void ensures_get_random_number_works_accurately() {

        mockedNumberApiServer.enqueue(new MockResponse()
                                          .setBody("{\n"
                                                       + "    \"text\": \"The number that explains everything.\",\n"
                                                       + "    \"found\": true,\n"
                                                       + "    \"number\": 42,\n"
                                                       + "    \"type\": \"year\",\n"
                                                       + "    \"date\": \"June 6\"\n"
                                                       + "}")
                                          .addHeader("Content-Type", "application/json"));

        this.webTestClient.get()
                          .uri("/numbers/random")
                          .accept(MediaType.APPLICATION_JSON)
                          .exchange()
                          .expectStatus()
                          .isOk()
                          .expectBody(NumberResponse.class)
                          .value(number -> number, equalTo(NumberResponse.from(42L, "The number that explains everything.")));
    }
}
