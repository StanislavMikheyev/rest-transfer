package com.github.stanislav.mikheyev.resttransfer.transaction;

import com.github.stanislav.mikheyev.resttransfer.account.Account;
import com.github.stanislav.mikheyev.resttransfer.account.AccountNotFoundException;
import com.github.stanislav.mikheyev.resttransfer.account.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private static final String INVALID_AMOUNT_ERROR_MESSAGE = "Amount should be greater than zero";
    private static final String INSUFFICIENT_FUNDS_ERROR_MESSAGE = "Source account has insufficient funds";
    private static final String ACCOUNT_CURRENCY_MISMATCH_ERROR_MESSAGE
            = "Source and target accounts have different currencies";
    private static final String SOURCE_AND_TARGET_ACCOUNTS_SHOULD_BE_DIFFERENT_ERROR_MESSAGE
            = "Source and target accounts should be different";
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

        if (createTransactionDto.getSourceAccountId().equals(createTransactionDto.getTargetAccountId())) {
            throw new TransactionNotAllowedException(
                    SOURCE_AND_TARGET_ACCOUNTS_SHOULD_BE_DIFFERENT_ERROR_MESSAGE);
        }

        Account sourceAccount = accountRepository
                .findById(createTransactionDto.getSourceAccountId())
                .orElseThrow(AccountNotFoundException::new);
        Account targetAccount = accountRepository
                .findById(createTransactionDto.getTargetAccountId())
                .orElseThrow(AccountNotFoundException::new);
        BigDecimal amount = createTransactionDto.getAmount();

        if (negativeOrZero(amount)) {
            throw new TransactionNotAllowedException(INVALID_AMOUNT_ERROR_MESSAGE);
        }
        if (newSourceBalanceIsNegativeAndSourceIsNotTreasury(sourceAccount, amount)) {
            throw new TransactionNotAllowedException(INSUFFICIENT_FUNDS_ERROR_MESSAGE);
        }
        if (!sourceAccount.getCurrency().equals(targetAccount.getCurrency())) {
            throw new TransactionNotAllowedException(ACCOUNT_CURRENCY_MISMATCH_ERROR_MESSAGE);
        }

        sourceAccount.setBalance(sourceAccount.getBalance().subtract(amount));
        targetAccount.setBalance(targetAccount.getBalance().add(amount));

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

    private boolean newSourceBalanceIsNegativeAndSourceIsNotTreasury(Account sourceAccount, BigDecimal amount) {
        return sourceAccount.getBalance().subtract(amount).compareTo(BigDecimal.ZERO) < 0
                && !sourceAccount.getTreasury();
    }

    private boolean negativeOrZero(BigDecimal amount) {
        return amount.compareTo(BigDecimal.ZERO) <= 0;
    }
}
