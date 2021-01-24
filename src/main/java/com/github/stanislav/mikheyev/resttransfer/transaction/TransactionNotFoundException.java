package com.github.stanislav.mikheyev.resttransfer.transaction;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class TransactionNotFoundException extends RuntimeException {
    public TransactionNotFoundException() {
        super("Transaction can not be found using provided data");
    }

    public TransactionNotFoundException(String message) {
        super(message);
    }
}
