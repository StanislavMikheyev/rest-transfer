package com.github.stanislav.mikheyev.resttransfer.transaction;

import com.github.stanislav.mikheyev.resttransfer.account.Account;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends CrudRepository<Transaction, Integer> {
    Iterable<Transaction> findAllBySourceAccountOrTargetAccount(Account sourceAccount, Account targetAccount);

    default Iterable<Transaction> findTransactionWithAccount(Account account) {
        return findAllBySourceAccountOrTargetAccount(account, account);
    }
}
