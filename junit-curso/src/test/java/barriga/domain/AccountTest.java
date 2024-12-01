package barriga.domain;

import barriga.domain.builders.AccountBuilder;

import static barriga.domain.builders.UserBuilder.aUser;
import static org.junit.jupiter.api.Assertions.*;

import barriga.exceptions.ValidationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

public class AccountTest {

    @Test
    public void shouldCreateValidAccount() {
        Account account = AccountBuilder.aAccount().now();

        assertAll("Account",
                () -> assertEquals(1L, account.getId()),
                () -> assertEquals("Valid Account", account.getName()),
                () -> assertEquals(aUser().now(), account.getUser()));
    }

    @ParameterizedTest
    @MethodSource(value = "dataProvider")
    public void shouldRejectInvalidAccount(Long id, String name, User user, String message) {
        ValidationException exception = assertThrows(
                ValidationException.class,
                () -> AccountBuilder.aAccount().withId(id).withName(name).withUser(user).now()
        );

        assertEquals(message, exception.getMessage());
    }

    private static Stream<Arguments> dataProvider() {
        return Stream.of(
                Arguments.of(1L, null, aUser().now(), "Name cannot be null or empty"),
                Arguments.of(1L, "Invalid account", null, "User cannot be null")
        );
    }
}
