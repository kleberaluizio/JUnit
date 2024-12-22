package barriga.repositories;

import barriga.domain.Transaction;

public interface TransactionDAO {
    Transaction save(Transaction transaction);
}
