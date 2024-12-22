package barriga.service;

import barriga.domain.Account;
import barriga.domain.User;
import barriga.events.AccountEvent;
import barriga.exceptions.EventException;
import barriga.exceptions.ValidationException;
import barriga.repositories.AccountRepository;

import java.time.LocalDateTime;
import java.util.List;

public class AccountService {

    private final AccountRepository repository;
    private AccountEvent event;

    public AccountService(AccountRepository repository, AccountEvent event) {
        this.repository = repository;
        this.event = event;
    }

    public Account save(Account accountToSave) throws ValidationException {
        findAccountByUser(accountToSave.getUser()).stream()
                .filter(account -> account.getName().equalsIgnoreCase(accountToSave.getName()))
                .findFirst()
                .ifPresent(existingAccount -> {
                    throw new ValidationException("User already has an account with provided name!");
                });

        Account savedAccount = this.repository.save(
                new Account(accountToSave.getId(), accountToSave.getName() + LocalDateTime.now(), accountToSave.getUser())
        );
        try{
            event.dispatch(savedAccount, AccountEvent.EventType.CREATED);
        } catch (EventException e) {
            repository.deleteAccount(savedAccount);
            throw new ValidationException("Account creation was not perform, failed to dispatch event!");
        }
        return savedAccount;
    }

    public List<Account> findAccountByUser(User user) {
        return repository.getAccountsByUser(user);
    }
}
