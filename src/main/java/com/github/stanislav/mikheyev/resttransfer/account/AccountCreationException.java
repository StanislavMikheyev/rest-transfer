package com.github.stanislav.mikheyev.resttransfer.account;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class AccountCreationException extends RuntimeException {
    public AccountCreationException() {
        super("Account data is invalid");
    }

    public AccountCreationException(String message) {
        super(message);
    }
}
