package com.github.stanislav.mikheyev.resttransfer.account;

import com.github.stanislav.mikheyev.resttransfer.currency.Currency;
import com.github.stanislav.mikheyev.resttransfer.currency.CurrencyRepository;
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

class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private CurrencyRepository currencyRepository;

    private AccountService accountService;

    @BeforeEach
    void beforeEach() {
        MockitoAnnotations.openMocks(this);
        accountService = new AccountService(accountRepository, currencyRepository);
    }

    @Test
    void getAllAccountsShouldReturnAllAccountsAsDto() {
        //given
        Integer id1 = 11;
        Integer id2 = 22;
        List<Account> accounts = new LinkedList<>();
        accounts.add(Account.builder().id(id1).build());
        accounts.add(Account.builder().id(id2).build());
        when(accountRepository.findAll()).thenReturn(accounts);

        //when
        Iterable<AccountDto> result = accountService.getAllAccounts();
        List<AccountDto> resultCollection = new LinkedList<>();
        result.forEach(resultCollection::add);

        //then
        assertAll(
                () -> assertEquals(resultCollection.size(), 2),
                () -> assertEquals(resultCollection.get(0).getId(), id1),
                () -> assertEquals(resultCollection.get(1).getId(), id2)

        );
    }

    @Test
    void getAccountByIdShouldReturnProperDto() {
        //given
        Integer id = 11;
        Account account = Account.builder()
                .id(id)
                .treasury(true)
                .balance(BigDecimal.TEN)
                .name("Test name")
                .currency(Currency.builder().code("code").build())
                .build();
        when(accountRepository.findById(id)).thenReturn(Optional.of(account));

        //when
        AccountDto result = accountService.getAccountById(id);

        //then
        assertAll(
                () -> assertEquals(result.getId(), account.getId()),
                () -> assertEquals(result.getName(), account.getName()),
                () -> assertEquals(result.getCurrencyCode(), account.getCurrency().getCode()),
                () -> assertEquals(result.getBalance(), account.getBalance()),
                () -> assertEquals(result.getTreasury(), account.getTreasury())
        );
    }

    @Test
    void createAccountShouldReturnNewAccountDto() {
        //given
        String currencyCode = "curr code";
        CreateAccountDto createAccountDto = CreateAccountDto.builder()
                .treasury(true)
                .balance(BigDecimal.TEN)
                .name("Test name")
                .currencyCode(currencyCode)
                .build();
        when(accountRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
        when(currencyRepository.findByCode(currencyCode))
                .thenReturn(Optional.of(Currency.builder().code(currencyCode).build()));

        //when
        AccountDto result = accountService.createAccount(createAccountDto);

        //then
        assertAll(
                () -> assertNull(result.getId()),
                () -> assertEquals(result.getName(), createAccountDto.getName()),
                () -> assertEquals(result.getCurrencyCode(), createAccountDto.getCurrencyCode()),
                () -> assertEquals(result.getBalance(), createAccountDto.getBalance()),
                () -> assertEquals(result.getTreasury(), createAccountDto.getTreasury())
        );
    }

    @Test
    void updateAccountShouldReturnModifiedAccountDto() {
        //given
        Integer id = 0;
        String oldName = "oldName";
        String newName = "newName";
        Account account = Account.builder()
                .id(id)
                .treasury(true)
                .balance(BigDecimal.TEN)
                .name(oldName)
                .currency(Currency.builder().code("code").build())
                .build();
        UpdateAccountDto updateAccountDto = UpdateAccountDto.builder().name(newName).build();
        when(accountRepository.findById(id)).thenReturn(Optional.of(account));
        when(accountRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        //when
        AccountDto result = accountService.updateAccount(id, updateAccountDto);

        //then
        assertAll(
                () -> assertEquals(result.getId(), account.getId()),
                () -> assertEquals(result.getName(), account.getName()),
                () -> assertEquals(result.getCurrencyCode(), account.getCurrency().getCode()),
                () -> assertEquals(result.getBalance(), account.getBalance()),
                () -> assertEquals(result.getTreasury(), account.getTreasury())
        );
    }
}