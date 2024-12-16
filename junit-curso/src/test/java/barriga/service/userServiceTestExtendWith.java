package barriga.service;

import barriga.domain.User;
import barriga.exceptions.ValidationException;
import barriga.repositories.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static barriga.domain.builders.UserBuilder.aUser;
import static org.mockito.Mockito.*;

// Ao anotar a classe com o @ExtendWith(MockitoExtension.class), não preciso mais
// do MockitoAnnotations.openMocks(this);
@ExtendWith(MockitoExtension.class)
public class userServiceTestExtendWith {
    @Mock
    private UserRepository repository;
    @InjectMocks
    private UserService service;
    private String EMAIL = "mail@mail.com";

    @Test
    public void shouldReturnEmptyWhenUserNotExist() {
        Mockito.when(repository.getUserByEmail(EMAIL)).thenReturn(Optional.empty());
        Optional<User> userOptional = service.findUserByEmail(EMAIL);

        Assertions.assertTrue(userOptional.isEmpty());
    }

    @Test
    public void shouldReturnUserWhenUserExists() {
        UserRepository repository = mock(UserRepository.class);
        service = new UserService(repository);
        Mockito.when(repository.getUserByEmail(EMAIL))
                .thenReturn(Optional.of(aUser().now()));

        Optional<User> existingOptional = service.findUserByEmail(EMAIL);
        Assertions.assertTrue(existingOptional.isPresent());

        Mockito.verify(repository).getUserByEmail(EMAIL);
    }

    @Test
    @DisplayName("USING TIMES")
    public void shouldReturnUserWhenUserExists1() {
        UserRepository repository = mock(UserRepository.class);
        service = new UserService(repository);

        Mockito.when(repository.getUserByEmail(EMAIL))
                .thenReturn(Optional.of(aUser().now()));

        Optional<User> existingOptional = service.findUserByEmail(EMAIL);
        Assertions.assertTrue(existingOptional.isPresent());

        // Verifica que o método foi invocado a quantidade de vezes indicada em times
        Mockito.verify(repository, Mockito.times(1)).getUserByEmail(EMAIL);

        // Verifica que o método foi invocado ao menos uma vez para o parametro fornecido
        Mockito.verify(repository, Mockito.atLeastOnce()).getUserByEmail(EMAIL);

        // Verifica que o método NUNCA foi invocado com o parametro "a@a.com"
        Mockito.verify(repository, Mockito.never()).getUserByEmail("a@a.com");

        // Verifica se realmente houve alguma iteração além das que eu estava esperando
        Mockito.verifyNoMoreInteractions(repository);
    }

    @Test
    public void shouldSaveUserWithSuccess() {
        // GIVEN
        User userToSave = aUser().withId(null).now();

        // WHEN
        //when(repository.getUserByEmail(userToSave.getEmail())).thenReturn(Optional.empty());
        when(repository.save(userToSave)).thenReturn(aUser().now());

        // THEN
        User savedUser = service.save(userToSave);
        Assertions.assertNotNull(savedUser.getId());

        verify(repository).getUserByEmail(userToSave.getEmail());
        //verify(repository).save(userToSave);
    }

    @Test
    public void shouldRejectExistingUser() {
        User userToSave = aUser().withId(null).withEmail("user1@gmail.com").now();
        Mockito.when(repository.getUserByEmail(userToSave.getEmail())).thenReturn(Optional.of(userToSave));

        ValidationException ex = Assertions.assertThrows(ValidationException.class, ()->{
            service.save(userToSave);
        });

        Assertions.assertTrue(ex.getMessage().endsWith("already exists!"));

        verify(repository, never()).save(userToSave); // assert .save must not be called!
    }
}
