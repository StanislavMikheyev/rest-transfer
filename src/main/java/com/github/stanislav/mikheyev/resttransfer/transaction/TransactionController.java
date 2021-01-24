package com.github.stanislav.mikheyev.resttransfer.transaction;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;

    @GetMapping("/transactions")
    public Iterable<TransactionDto> getTransactions() {
        return transactionService.getAllTransactions();
    }

    @GetMapping("/transaction/{transactionId}")
    public TransactionDto getTransaction(@PathVariable Integer transactionId) {
        return transactionService.getTransactionById(transactionId);
    }

    @PostMapping("/transaction")
    public TransactionDto createTransaction(@RequestBody CreateTransactionDto createTransactionDto) {
        return transactionService.createTransaction(createTransactionDto);
    }
}
