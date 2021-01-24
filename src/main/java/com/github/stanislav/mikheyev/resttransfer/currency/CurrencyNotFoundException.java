package com.github.stanislav.mikheyev.resttransfer.currency;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class CurrencyNotFoundException extends RuntimeException {
    public CurrencyNotFoundException() {
        super("Currency can not be found using provided data");
    }

    public CurrencyNotFoundException(String message) {
        super(message);
    }
}
