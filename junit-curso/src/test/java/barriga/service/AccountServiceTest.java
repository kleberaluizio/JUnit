package barriga.service;

import barriga.domain.Account;
import barriga.events.AccountEvent;
import barriga.exceptions.EventException;
import barriga.exceptions.ValidationException;
import barriga.repositories.AccountRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static barriga.domain.builders.AccountBuilder.aAccount;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @Mock private AccountRepository repository;
    @Mock private AccountEvent event;
    @InjectMocks private AccountService service;
    @Captor private ArgumentCaptor<Account> accountCaptor;

    @Test
    public void shouldSaveAccountWithSuccess() {
        // GIVEN
        Account accountToSave = aAccount().withId(null).now();

        // WHEN
        when(repository.save(Mockito.any(Account.class))).thenReturn(aAccount().now());
        doNothing().when(event).dispatch(aAccount().now(), AccountEvent.EventType.CREATED); // Mockito execute the verify for this action, parameters must be correctly

        // THEN
        Account savedAccount = service.save(accountToSave);
        Assertions.assertNotNull(savedAccount.getId());
        verify(repository).save(accountCaptor.capture());
        Assertions.assertTrue(accountCaptor.getValue().getName().startsWith(accountToSave.getName()));
    }

    @Test
    public void shouldSaveAccountWithSuccessWhenHasAccounts() {
        // GIVEN
        Account accountToSave = aAccount().withId(null).now();

        // WHEN
        when(repository.getAccountsByUser(accountToSave.getUser())).thenReturn(List.of(aAccount().withName("Other account").now()));
        when(repository.save(Mockito.any(Account.class))).thenReturn(aAccount().now());

        // THEN
        Account savedAccount = service.save(accountToSave);
        Assertions.assertNotNull(savedAccount.getId());
    }

    @Test
    public void shouldRejectDuplicateAccount() {
        // GIVEN
        Account accountToSave = aAccount().withId(null).now();

        // WHEN
        when(repository.getAccountsByUser(accountToSave.getUser())).thenReturn(List.of(accountToSave));

        // THEN
        ValidationException exception = Assertions.assertThrows(ValidationException.class, () -> service.save(accountToSave));
        Assertions.assertEquals("User already has an account with provided name!",exception.getMessage());
    }

    @Test
    public void shouldNotKeepAccountWithoutEvent() {
        // GIVEN
        Account accountToSave = aAccount().withId(null).now();
        Account savedAccount = aAccount().now();
        String EXCEPTION_MESSAGE = "Account creation was not perform, failed to dispatch event!";

        // WHEN
        when(repository.save(Mockito.any(Account.class))).thenReturn(savedAccount);
        doThrow(new EventException(EXCEPTION_MESSAGE)).when(event).dispatch(savedAccount, AccountEvent.EventType.CREATED); // Mockito execute the verify for this action, parameters must be correctly

        // THEN
        String message = Assertions.assertThrows(ValidationException.class, () -> service.save(accountToSave)).getMessage();
        Assertions.assertEquals(EXCEPTION_MESSAGE, message);
        verify(repository, times(1)).deleteAccount(savedAccount);
    }
}