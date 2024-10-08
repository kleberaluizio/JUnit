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

        assertEquals(name, user.getName());
        assertEquals(email, user.getEmail());
        assertEquals(password, user.getPassword());

    }
}