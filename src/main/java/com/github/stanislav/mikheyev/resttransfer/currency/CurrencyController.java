package com.github.stanislav.mikheyev.resttransfer.currency;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
public class CurrencyController {
    private final CurrencyService service;

    @GetMapping("/currencies")
    public Iterable<CurrencyDto> getCurrencies() {
        return service.getAllCurrencies();
    }

    @GetMapping("/currency/{id}")
    public CurrencyDto getCurrency(@PathVariable Integer id) {
        return service.getCurrencyById(id);
    }

}
