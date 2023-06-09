package com.adeo.sample.user.beans;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("User beans test case.")
class UserTest {

    @Test
    @DisplayName("Ensures creating user works accurately.")
    void ensures_creating_user_works_accurately() {

        Assertions.assertThat(new User(1L, "Foo", "Bar").toString())
                  .hasToString("User[id=1, firstName=Foo, lastName=Bar]");
    }
}
