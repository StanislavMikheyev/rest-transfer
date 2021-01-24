package com.github.stanislav.mikheyev.resttransfer.account;

import com.github.stanislav.mikheyev.resttransfer.currency.Currency;
import com.github.stanislav.mikheyev.resttransfer.currency.CurrencyNotFoundException;
import com.github.stanislav.mikheyev.resttransfer.currency.CurrencyRepository;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class AccountService {
    private static final String NON_TREASURY_WITH_NEGATIVE_BALANCE = "Only treasury accounts can have negative balance";
    private final AccountRepository accountRepository;
    private final CurrencyRepository currencyRepository;
    private final AccountMapper mapper = Mappers.getMapper(AccountMapper.class);

    public Iterable<AccountDto> getAllAccounts() {
        return mapper.entityToDto(accountRepository.findAll());
    }

    public AccountDto getAccountById(Integer id) {
        return mapper.entityToDto(
                accountRepository.findById(id).orElseThrow(AccountNotFoundException::new)
        );
    }

    public AccountDto createAccount(CreateAccountDto dto) {

        if (accountBalanceNegativeAndNotTreasury(dto)) {
            throw new AccountCreationException(NON_TREASURY_WITH_NEGATIVE_BALANCE);
        }

        Account newAccount = Account.builder()
                .id(null)
                .balance(resolveBalance(dto.getBalance()))
                .treasury(dto.getTreasury())
                .currency(resolveCurrency(dto.getCurrencyCode()))
                .name(dto.getName())
                .build();
        accountRepository.save(newAccount);
        return mapper.entityToDto(newAccount);
    }

    public AccountDto updateAccount(Integer id, UpdateAccountDto dto) {
        Account account = accountRepository.findById(id).orElseThrow(AccountNotFoundException::new);
        if (dto.getName() != null) {
            account.setName(dto.getName());
        }
        accountRepository.save(account);
        return mapper.entityToDto(account);
    }

    private BigDecimal resolveBalance(BigDecimal balance) {
        return balance == null ? BigDecimal.ZERO : balance;
    }

    private Currency resolveCurrency(String currencyCode) {
        return currencyRepository.findByCode(currencyCode).orElseThrow(CurrencyNotFoundException::new);
    }

    private boolean accountBalanceNegativeAndNotTreasury(CreateAccountDto dto) {
        return dto.getBalance().compareTo(BigDecimal.ZERO) < 0 && !dto.getTreasury();
    }
}
