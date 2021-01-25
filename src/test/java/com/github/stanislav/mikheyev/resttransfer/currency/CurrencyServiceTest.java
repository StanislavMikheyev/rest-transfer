package com.github.stanislav.mikheyev.resttransfer.currency;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class CurrencyServiceTest {

    @Mock
    private CurrencyRepository currencyRepository;

    private CurrencyService currencyService;

    @BeforeEach
    void beforeEach() {
        MockitoAnnotations.openMocks(this);
        currencyService = new CurrencyService(currencyRepository);
    }

    @Test
    void getCurrencyByIdShouldReturnProperDto() {
        //given
        Integer id = 11;
        Currency currency = Currency.builder()
                .id(id)
                .name("Test name")
                .code("TST")
                .build();
        when(currencyRepository.findById(id)).thenReturn(Optional.of(currency));

        //when
        CurrencyDto result = currencyService.getCurrencyById(id);

        //then
        assertAll(
                () -> assertEquals(result.getId(), currency.getId()),
                () -> assertEquals(result.getName(), currency.getName()),
                () -> assertEquals(result.getCode(), currency.getCode())
        );
    }

    @Test
    void getAllCurrenciesShouldReturnCurrenciesAsDto() {
        //given
        Integer id1 = 11;
        Integer id2 = 22;
        List<Currency> currencies = new LinkedList<>();
        currencies.add(Currency.builder().id(id1).build());
        currencies.add(Currency.builder().id(id2).build());
        when(currencyRepository.findAll()).thenReturn(currencies);

        //when
        Iterable<CurrencyDto> result = currencyService.getAllCurrencies();
        List<CurrencyDto> resultCollection = new LinkedList<>();
        result.forEach(resultCollection::add);

        //then
        assertAll(
                () -> assertEquals(resultCollection.size(), 2),
                () -> assertEquals(resultCollection.get(0).getId(), id1),
                () -> assertEquals(resultCollection.get(1).getId(), id2)

        );
    }
}