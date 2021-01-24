package com.github.stanislav.mikheyev.resttransfer.currency;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Currency can not be found using provided data")
public class CurrencyNotFoundException extends RuntimeException {
}
