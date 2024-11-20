package barriga.domain;

import barriga.domain.builders.AccountBuilder;
import barriga.domain.builders.UserBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AccountTest {

    @Test
    public void shouldCreateValidAccount() {
        Account account = AccountBuilder.aAccount().now();

        Assertions.assertAll("Account",
                () -> Assertions.assertEquals(1L, account.getId()),
                () -> Assertions.assertEquals("Valid Account", account.getName()),
                () -> Assertions.assertEquals(UserBuilder.aUser().now(), account.getUser()));
    }
}
