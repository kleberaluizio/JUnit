package barriga.service;

import barriga.domain.Account;
import barriga.repositories.AccountRepository;

public class AccountService {

    private final AccountRepository repository;

    public AccountService(AccountRepository repository) {
        this.repository = repository;
    }

    public Account save(Account accountToSave) {
        return this.repository.save(accountToSave);
    }
}
