package barriga.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Domain: User")
class UserTest {

    @Test
    @DisplayName("Should Create Valid User When Data Is Valid")
    public void shouldCreateValidUserWhenDataIsValid() {
        String name = "John John";
        String email = "john@john.com";
        String password = "password";
        User user = new User(null, name, email, password);

        assertEquals("John", user.getName());
        assertEquals("john@john.coma", user.getEmail());
        assertEquals(password, user.getPassword());

    }

    @Test
    @DisplayName("Refatora o teste anterior de modo a garantir que todos os assertions sejam executados")
    public void shouldCreateValidUserWhenDataIsValid_ASSERT_ALL() {
        String name = "John John";
        String email = "john@john.com";
        String password = "password";
        User user = new User(null, name, email, password);

        Assertions.assertAll("User",
            ()-> assertEquals("John", user.getName()),
            ()-> assertEquals("john@john.com", user.getEmail()),
            ()-> assertEquals("passworda", user.getPassword())
        );
    }


}