package com.github.stanislav.mikheyev.resttransfer.account;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class AccountNotFoundException extends RuntimeException {
    public AccountNotFoundException() {
        super("Account can not be found using provided data");
    }

    public AccountNotFoundException(String message) {
        super(message);
    }
}
