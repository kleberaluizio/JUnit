package barriga.service;

import barriga.domain.Account;
import barriga.exceptions.ValidationException;
import barriga.repositories.AccountRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static barriga.domain.builders.AccountBuilder.aAccount;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @Mock
    private AccountRepository repository;
    @InjectMocks
    private AccountService service;

    @Test
    public void shouldSaveAccountWithSuccess() {
        // GIVEN
        Account accountToSave = aAccount().withId(null).now();

        // WHEN
        when(repository.save(accountToSave)).thenReturn(aAccount().now());

        // THEN
        Account savedAccount = service.save(accountToSave);
        Assertions.assertNotNull(savedAccount.getId());
    }

    @Test
    public void shouldSaveAccountWithSuccessWhenHasAccounts() {
        // GIVEN
        Account accountToSave = aAccount().withId(null).now();

        // WHEN
        when(repository.getAccountsByUser(accountToSave.getUser())).thenReturn(List.of(aAccount().withName("Other account").now()));
        when(repository.save(accountToSave)).thenReturn(aAccount().now());

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
}