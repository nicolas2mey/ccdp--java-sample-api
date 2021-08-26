package com.adeo.sample.number.services;

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
import reactor.test.StepVerifier;

import java.io.IOException;

@DisplayName("Number service test case")
@WebFluxTest(NumberService.class)
@Import(TestContext.class)
class NumberServiceTest {

    private static MockWebServer mockedNumberApiServer;

    @Autowired
    NumberService numberService;

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
    @DisplayName("Ensures get next random number works accurately.")
    void ensures_get_next_random_number_works_accurately() {

        mockedNumberApiServer.enqueue(new MockResponse()
                                          .setBody("{\n"
                                                       + "    \"text\": \"The number that explains everything.\",\n"
                                                       + "    \"found\": true,\n"
                                                       + "    \"number\": 42,\n"
                                                       + "    \"type\": \"year\",\n"
                                                       + "    \"date\": \"June 6\"\n"
                                                       + "}")
                                          .addHeader("Content-Type", "application/json"));

        StepVerifier.create(numberService.nextRandom())
                    .expectNextMatches(
                        response -> response.equals(NumberResponse.from(42L, "The number that explains everything."))
                    )
                    .verifyComplete();
    }
}
