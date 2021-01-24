package com.github.stanislav.mikheyev.resttransfer.currency;

import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CurrencyService {
    private final CurrencyRepository repository;
    private final CurrencyMapper mapper = Mappers.getMapper(CurrencyMapper.class);

    public CurrencyDto getCurrencyById(Integer id) {
        return mapper.entityToDto(repository.findById(id).orElseThrow(CurrencyNotFoundException::new));
    }

    public Iterable<CurrencyDto> getAllCurrencies() {
        return mapper.entityToDto(repository.findAll());
    }
}
