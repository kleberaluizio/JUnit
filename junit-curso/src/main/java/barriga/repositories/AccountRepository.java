package barriga.repositories;

import barriga.domain.Account;

import java.util.Optional;

public interface AccountRepository {

    Account save(Account account);

    Optional<Account> getAccountByName(String name);
}
