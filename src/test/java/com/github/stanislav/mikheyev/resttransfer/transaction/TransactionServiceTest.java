package com.github.stanislav.mikheyev.resttransfer.transaction;

import com.github.stanislav.mikheyev.resttransfer.account.Account;
import com.github.stanislav.mikheyev.resttransfer.account.AccountRepository;
import com.github.stanislav.mikheyev.resttransfer.currency.Currency;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private AccountRepository accountRepository;

    private TransactionService transactionService;

    @BeforeEach
    void beforeEach() {
        MockitoAnnotations.openMocks(this);
        transactionService = new TransactionService(transactionRepository, accountRepository);
    }

    @Test
    void getAllTransactions() {
        //given
        Integer id1 = 11;
        Integer id2 = 22;
        List<Transaction> transactions = new LinkedList<>();
        transactions.add(Transaction.builder().id(id1).build());
        transactions.add(Transaction.builder().id(id2).build());
        when(transactionRepository.findAll()).thenReturn(transactions);

        //when
        Iterable<TransactionDto> result = transactionService.getAllTransactions();
        List<TransactionDto> resultCollection = new LinkedList<>();
        result.forEach(resultCollection::add);

        //then
        assertAll(
                () -> assertEquals(resultCollection.size(), 2),
                () -> assertEquals(resultCollection.get(0).getId(), id1),
                () -> assertEquals(resultCollection.get(1).getId(), id2)

        );
    }

    @Test
    void getTransactionByIdShouldReturnProperDto() {
        //given
        Integer id = 11;
        Transaction transaction = Transaction.builder()
                .id(id)
                .targetAccount(Account.builder().id(0).build())
                .sourceAccount(Account.builder().id(1).build())
                .sourceSnapshotAfterTransaction(BigDecimal.ZERO)
                .targetSnapshotAfterTransaction(BigDecimal.TEN)
                .amount(BigDecimal.TEN)
                .build();
        when(transactionRepository.findById(id)).thenReturn(Optional.of(transaction));

        //when
        TransactionDto result = transactionService.getTransactionById(id);

        //then
        assertAll(
                () -> assertEquals(result.getId(), transaction.getId()),
                () -> assertEquals(result.getTargetAccountId(), transaction.getTargetAccount().getId()),
                () -> assertEquals(result.getSourceAccountId(), transaction.getSourceAccount().getId()),
                () -> assertEquals(
                        result.getSourceSnapshotAfterTransaction(), transaction.getSourceSnapshotAfterTransaction()),
                () -> assertEquals(
                        result.getTargetSnapshotAfterTransaction(), transaction.getTargetSnapshotAfterTransaction()),
                () -> assertEquals(result.getAmount(), transaction.getAmount())
        );
    }

    @Test
    void createTransaction() {
        //given
        Currency currency = Currency.builder().build();
        Account sourceAccount = Account.builder().id(0).balance(BigDecimal.TEN).currency(currency).build();
        Account targetAccount = Account.builder().id(1).balance(BigDecimal.ZERO).currency(currency).build();
        CreateTransactionDto createTransactionDto = CreateTransactionDto.builder()
                .amount(BigDecimal.TEN)
                .sourceAccountId(sourceAccount.getId())
                .targetAccountId(targetAccount.getId())
                .build();
        when(accountRepository.findById(sourceAccount.getId())).thenReturn(Optional.of(sourceAccount));
        when(accountRepository.findById(targetAccount.getId())).thenReturn(Optional.of(targetAccount));
        when(transactionRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        //when
        TransactionDto result = transactionService.createTransaction(createTransactionDto);

        //then
        assertAll(
                () -> assertNull(result.getId()),
                () -> assertEquals(result.getSourceAccountId(), sourceAccount.getId()),
                () -> assertEquals(result.getTargetAccountId(), targetAccount.getId()),
                () -> assertEquals(result.getSourceSnapshotAfterTransaction(), BigDecimal.ZERO),
                () -> assertEquals(result.getTargetSnapshotAfterTransaction(), BigDecimal.TEN),
                () -> assertEquals(result.getAmount(), BigDecimal.TEN)
        );
    }
}