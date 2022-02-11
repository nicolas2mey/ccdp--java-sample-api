package com.adeo.sample.user.factories;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("User factory test case.")
class UserFactoryTest {

    @BeforeAll
    static void setUp() {
        UserFactory.reset();
    }

    @AfterAll
    static void tearDown() {
        UserFactory.reset();
    }

    @ParameterizedTest(name = "Ensures creating user with inputs {0}/{1} fails.")
    @MethodSource("invalidInputs")
    void ensures_invalid_inputs_throws_IAE(final String firstName, final String lastName) {

        assertThatThrownBy(() -> UserFactory.create(firstName, lastName))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("Ensures creating users works accurately.")
    @SuppressWarnings("PMD.JUnitTestContainsTooManyAsserts")
    void ensures_creating_users_works_accurately() {

        assertThat(UserFactory.create("Foo1", "Bar1"))
            .hasToString("User[id=1, firstName=Foo1, lastName=Bar1]");

        assertThat(UserFactory.create("Foo2", "Bar2"))
            .hasToString("User[id=2, firstName=Foo2, lastName=Bar2]");
    }

    private static Stream<Arguments> invalidInputs() {

        return Stream.of(
            Arguments.of(null, null),
            Arguments.of(null, "Bar"),
            Arguments.of("Foo", null),
            Arguments.of("", ""),
            Arguments.of("", "Bar"),
            Arguments.of("Foo", "")
        );
    }
}
