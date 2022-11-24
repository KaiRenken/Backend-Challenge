package com.example.test3.service;

import com.example.test3.domain.Person;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PersonParserTest {
    private final PersonParser parser = new PersonParser();

    @Test
    void AllPersonInfo() {
        Person person = parser.parse("Dr. Frank von Supper (fsupper)");
        assertThat(person.getFirstName()).isEqualTo("Frank");
        assertThat(person.getLastName()).isEqualTo("Supper");
        assertThat(person.getLdapUser()).isEqualTo("fsupper");
        assertThat(person.getTitle()).isEqualTo("Dr.");
        assertThat(person.getNameAddition()).isEqualTo("von");
    }

    @Test
    void personOneTitle() {
        Person person = parser.parse("Frank von Supper (fsupper)");
        assertThat(person.getFirstName()).isEqualTo("Frank");
        assertThat(person.getLastName()).isEqualTo("Supper");
        assertThat(person.getLdapUser()).isEqualTo("fsupper");
        assertThat(person.getNameAddition()).isEqualTo("von");
        assertThat(person.getTitle()).isEqualTo("");
    }

    @Test
    void personOneTitleAndOneNameAddition() {
        Person person = parser.parse("Frank Supper (fsupper)");
        assertThat(person.getFirstName()).isEqualTo("Frank");
        assertThat(person.getLastName()).isEqualTo("Supper");
        assertThat(person.getLdapUser()).isEqualTo("fsupper");
        assertThat(person.getNameAddition()).isEqualTo("");
        assertThat(person.getTitle()).isEqualTo("");
    }

    @Test
    void personOneDate() {
        Person person = parser.parse("");
        assertThat(person.getFirstName()).isEqualTo("");
        assertThat(person.getLastName()).isEqualTo("");
        assertThat(person.getLdapUser()).isEqualTo("");
        assertThat(person.getNameAddition()).isEqualTo("");
        assertThat(person.getTitle()).isEqualTo("");
    }

    @Test
    void personHasOnlyLastname() {
        assertThatThrownBy(() -> parser.parse("Supper"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Person is not complete");
    }

    @Test
    void personHasOnlyFirstnameAndLastname() {
        assertThatThrownBy(() -> parser.parse("Frank Supper"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Person is not complete");
    }

    @Test
    void personHasWrongTitle() {
        assertThatThrownBy(() -> parser.parse("Professor"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Person is not complete");
    }

    @Test
    void personHasNotLdapUser() {
        assertThatThrownBy(() -> parser.parse("Dr. Frank Supper"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Person is not complete");
    }

    @Test
    void personHasWrongTitleLastnameLdapUser() {
        assertThatThrownBy(() -> parser.parse("Prof. Max Müller (ldap)"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Title is not correct");
    }

    @Test
    void personHasFirstnameAdditionUndLastname() {
        assertThatThrownBy(() -> parser.parse(" Frank von Supper "))
                .isInstanceOf(IllegalArgumentException.class).hasMessageContaining("Person is not complete");
    }

    @Test
    void personHasTitleFirstnameLdapUser() {
        assertThatThrownBy(() -> parser.parse(" Dr. Frank (fsupper)"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Person is not complete");
    }

    @Test
    void personHasTitleFirstnameNameAdditionLastname() {
        assertThatThrownBy(() -> parser.parse(" Dr. Frank von Supper"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Person is not complete");
    }

    @Test
    void personHasTitleFirstnameAdditionLastnameLdapUser() {
        assertThatThrownBy(() -> parser.parse(" Dr. von Supper(fsupper)"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Person is not complete");
    }

    @Test
    void personHasTitleFirstnameSecondNameAdditionLastname() {
        assertThatThrownBy(() -> parser.parse(" Dr.Frank Max von Supper"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Person is not complete");
    }

    @Test
    void personHasTitleFirstnameSecondNameSecondNameLastnameNotTitle() {
        assertThatThrownBy(() -> parser.parse(" Dr.Frank Max Dieter Supper"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Person is not complete");
    }

    @Test
    void personHasWrongTitleFirstnameAdditionLastnameLdapUser() {
        assertThatThrownBy(() -> parser.parse("Prof.Frank von  Supper (fsupper)"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Title is not correct");
    }

    @Test
    void personHasTitleFirstNameAdditionLastnameLdpUserNotLastBracket() {
        assertThatThrownBy(() -> parser.parse("Dr.Frank von  Supper (fsupper"))
                .isInstanceOf(IllegalArgumentException.class).hasMessageContaining("Person is not complete");
    }

    @Test
    void personHasTitleFirstNameAdditionLastnameLdpUserNotStartBracket() {
        assertThatThrownBy(() -> parser.parse("Dr.Frank von  Supper fsupper)"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Person is not complete");
    }
}
