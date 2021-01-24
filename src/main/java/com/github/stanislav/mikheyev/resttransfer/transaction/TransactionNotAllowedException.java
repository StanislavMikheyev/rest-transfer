package com.github.stanislav.mikheyev.resttransfer.transaction;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class TransactionNotAllowedException extends RuntimeException {
    public TransactionNotAllowedException() {
        super("Given transaction is not allowed");
    }

    public TransactionNotAllowedException(String message) {
        super(message);
    }
}
