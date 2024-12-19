package barriga.service;

import barriga.domain.Account;
import barriga.domain.User;
import barriga.exceptions.ValidationException;
import barriga.repositories.AccountRepository;

import java.util.List;

public class AccountService {

    private final AccountRepository repository;

    public AccountService(AccountRepository repository) {
        this.repository = repository;
    }

    public Account save(Account accountToSave) {
        findAccountByUser(accountToSave.getUser()).stream()
                .filter(account -> account.getName().equalsIgnoreCase(accountToSave.getName()))
                .findFirst()
                .ifPresent(existingAccount -> {
                    throw new ValidationException("User already has an account with provided name!");
                });
        return this.repository.save(accountToSave);
    }

    public List<Account> findAccountByUser(User user) {
        return repository.getAccountsByUser(user);
    }
}
