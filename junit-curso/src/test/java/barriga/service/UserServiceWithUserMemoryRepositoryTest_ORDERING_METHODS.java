package barriga.service;

import barriga.domain.User;
import barriga.exceptions.ValidationException;
import barriga.repositories.UserMemoryRepository;
import org.junit.jupiter.api.*;

import static barriga.domain.builders.UserBuilder.aUser;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserServiceWithUserMemoryRepositoryTestOrderingMethods {
    private static final UserService service = new UserService(new UserMemoryRepository());

    @Test
    @DisplayName("Should save valid user")
    @Order(1)
    void shouldSaveValidUser() {
        User user = service.save(aUser().withId(null).now());
        Assertions.assertNotNull(user.getId());

    }

    @Test
    @Order(2)
    @DisplayName("Should reject existing user")
    void shouldRejectExistingUser() {
        ValidationException exception = Assertions.assertThrows(ValidationException.class, () -> service.save(aUser().withId(null).now()));
        Assertions.assertEquals("User john@john.com already exists!", exception.getMessage());
    }
}