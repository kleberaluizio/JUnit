package barriga.repositories;

import barriga.domain.Account;
import barriga.domain.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AccountMemoryRepository implements AccountRepository{
    private final List<Account> accounts;
    private static Long currentAccountId;

    public AccountMemoryRepository() {
        currentAccountId = 0L;
        accounts = new ArrayList<>();
        User firstUser = new User(null, "User #1", "user1@gmail.com", "123456");
        save(new Account(null, "Account #1", firstUser));
    }

    @Override
    public Account save(Account account) {
        Account newAccount = new Account(nextAccountId(), account.getName(), account.getUser());
        accounts.add(newAccount);
        return newAccount;
    }

    @Override
    public List<Account> getAccountsByUser(User user) {
        return accounts.stream().filter(account -> account.getUser().equals(user)).toList();
    }

    private Long nextAccountId() {
        return ++currentAccountId;
    }
}
