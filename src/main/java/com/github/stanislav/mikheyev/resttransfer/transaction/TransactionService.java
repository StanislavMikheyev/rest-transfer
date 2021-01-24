package com.github.stanislav.mikheyev.resttransfer.transaction;

import com.github.stanislav.mikheyev.resttransfer.account.Account;
import com.github.stanislav.mikheyev.resttransfer.account.AccountNotFoundException;
import com.github.stanislav.mikheyev.resttransfer.account.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private final TransactionMapper transactionMapper = Mappers.getMapper(TransactionMapper.class);

    public Iterable<TransactionDto> getAllTransactions() {
        return transactionMapper.entityToDto(transactionRepository.findAll());
    }

    public TransactionDto getTransactionById(Integer transactionId) {
        return transactionMapper.entityToDto(
                transactionRepository.findById(transactionId).orElseThrow(TransactionNotFoundException::new)
        );
    }

    public TransactionDto createTransaction(CreateTransactionDto createTransactionDto) {
        Account sourceAccount = accountRepository
                .findById(createTransactionDto.getSourceAccountId())
                .orElseThrow(AccountNotFoundException::new);
        Account targetAccount = accountRepository
                .findById(createTransactionDto.getTargetAccountId())
                .orElseThrow(AccountNotFoundException::new);

        Transaction newTransaction = Transaction.builder()
                .sourceAccount(sourceAccount)
                .targetAccount(targetAccount)
                .amount(createTransactionDto.getAmount())
                .sourceSnapshotAfterTransaction(sourceAccount.getBalance())
                .targetSnapshotAfterTransaction(targetAccount.getBalance())
                .build();

        transactionRepository.save(newTransaction);
        accountRepository.save(sourceAccount);
        accountRepository.save(targetAccount);

        return transactionMapper.entityToDto(newTransaction);
    }
}
