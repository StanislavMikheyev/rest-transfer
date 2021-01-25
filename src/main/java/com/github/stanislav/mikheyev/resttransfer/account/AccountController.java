package com.github.stanislav.mikheyev.resttransfer.account;

import com.github.stanislav.mikheyev.resttransfer.transaction.TransactionDto;
import com.github.stanislav.mikheyev.resttransfer.transaction.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;
    private final TransactionService transactionService;

    @GetMapping("/accounts")
    public Iterable<AccountDto> getAccounts() {
        return accountService.getAllAccounts();
    }

    @GetMapping("/account/{id}")
    public AccountDto getAccount(@PathVariable Integer id) {
        return accountService.getAccountById(id);
    }

    @PostMapping("/account")
    public AccountDto createAccount(@RequestBody CreateAccountDto dto) {
        return accountService.createAccount(dto);
    }

    @PatchMapping("/account/{id}")
    public AccountDto updateAccount(@PathVariable Integer id, @RequestBody UpdateAccountDto dto) {
        return accountService.updateAccount(id, dto);
    }

    @GetMapping("/account/{accountId}/transactions")
    public Iterable<TransactionDto> getTransactionsForAccount(@PathVariable Integer accountId) {
        return transactionService.getTransactionsForAccount(accountId);
    }
}
