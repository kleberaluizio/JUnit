package barriga.service;

import barriga.domain.Account;
import barriga.exceptions.ValidationException;
import barriga.repositories.AccountRepository;

import java.util.Optional;

public class AccountService {

    private final AccountRepository repository;

    public AccountService(AccountRepository repository) {
        this.repository = repository;
    }

    public Account save(Account accountToSave) {
        findAccountByName(accountToSave.getName())
                .filter(existingAccount -> existingAccount.getUser().equals(accountToSave.getUser()))
                .ifPresent(existingAccount -> {
                    throw new ValidationException(String.format("Account name %s already exists!", existingAccount.getName()));
                });
        return this.repository.save(accountToSave);
    }

    public Optional<Account> findAccountByName(String name) {
        return repository.getAccountByName(name);
    }
}
