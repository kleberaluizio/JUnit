package barriga.domain;

import barriga.domain.builders.UserBuilder;
import barriga.exceptions.ValidationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

@Tag("domain")
@Tag("user")
@DisplayName("Domain: User")
public class UserTest {

    @Test
    @DisplayName("Should Create Valid User When Data Is Valid")
    public void shouldCreateValidUserWhenDataIsValid() {
        String name = "John John";
        String email = "john@john.com";
        String password = "password";
        User user = new User(null, name, email, password);

        assertEquals("John John", user.getName());
        assertEquals("john@john.com", user.getEmail());
        assertEquals(password, user.getPassword());

    }

    @Test
    @DisplayName("Refatora o teste anterior de modo a garantir que todos os assertions sejam executados")
    public void shouldCreateValidUserWhenDataIsValid_ASSERT_ALL() {
        User user = UserBuilder.aUser().withName("Paul").now();

        Assertions.assertAll("User",
            ()-> assertEquals("Paul", user.getName()),
            ()-> assertEquals("john@john.com", user.getEmail()),
            ()-> assertEquals("password", user.getPassword())
        );
    }

    @Test
    @DisplayName("Should reject user when name is not provided")
    public void shouldRejectUserWhenNameIsNotProvided() {
        ValidationException exception = assertThrows(ValidationException.class,  ()-> UserBuilder.aUser().withName(null).now());
        Assertions.assertEquals("Name cannot be null or empty", exception.getMessage());
    }

    @Test
    @DisplayName("Should reject user when e-mail is not provided")
    public void shouldRejectUserWhenEmailIsNotProvided() {
        ValidationException exception = assertThrows(ValidationException.class,  ()-> UserBuilder.aUser().withEmail("").now());
        Assertions.assertEquals("E-mail cannot be null or empty", exception.getMessage());
    }
    @Test
    @DisplayName("Should reject user when password is not provided")
    public void shouldRejectUserWhenPasswordIsNotProvided() {
        ValidationException exception = assertThrows(ValidationException.class,  ()-> UserBuilder.aUser().withPassword(null).now());
        Assertions.assertEquals("Password cannot be null or empty", exception.getMessage());
    }

    @DisplayName("Should Reject User When")
    @ParameterizedTest(name = "{4}")
    @CsvSource(value = {
            "NULL, josef@email.com, password, Name cannot be null or empty, ... name is null or empty",
            "Josef, N/A, password, E-mail cannot be null or empty, ... e-mail is null or empty",
            "Josef, josef@email.com, undefined, Password cannot be null or empty, ... password is null or empty",},
            nullValues = {"NULL", "N/A", "undefined"})
    void shouldRejectUserWhen(String name, String email, String password, String expectedExceptionMessage, String displayMessage) {
        ValidationException exception = assertThrows(ValidationException.class, () ->
                {
                    UserBuilder.aUser().withName(name).withEmail(email).withPassword(password).now();
                }
        );
        Assertions.assertEquals(expectedExceptionMessage, exception.getMessage());

    }

    // Using CSV File
    @DisplayName("Should Reject User When")
    @ParameterizedTest(name = "{4}")
    @CsvFileSource(
            files = "src/test/resources/userMandatoryFields.csv",
            nullValues = {"NULL", "N/A", "undefined"},
            numLinesToSkip = 1
    )
    void shouldRejectUserWhen2(String name, String email, String password, String expectedExceptionMessage, String displayMessage) {
        ValidationException exception = assertThrows(ValidationException.class, () ->
                {
                    UserBuilder.aUser().withName(name).withEmail(email).withPassword(password).now();
                }
        );
        Assertions.assertEquals(expectedExceptionMessage, exception.getMessage());

    }

    // Using CSV file and header = true
    @DisplayName("Should Reject User When")
    @ParameterizedTest
    @CsvFileSource(
            files = "src/test/resources/userMandatoryFields.csv",
            nullValues = {"NULL", "N/A", "undefined"},
            useHeadersInDisplayName = true
    )
    void shouldRejectUserWhen3(String name, String email, String password, String expectedExceptionMessage, String displayMessage) {
        ValidationException exception = assertThrows(ValidationException.class, () ->
                {
                    UserBuilder.aUser().withName(name).withEmail(email).withPassword(password).now();
                }
        );
        Assertions.assertEquals(expectedExceptionMessage, exception.getMessage());

    }
}