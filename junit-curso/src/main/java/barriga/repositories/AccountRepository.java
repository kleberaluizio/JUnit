package barriga.repositories;

import barriga.domain.Account;

import java.util.List;

public interface AccountRepository {

    Account save(Account account);

    List<Account> findAll();
}
