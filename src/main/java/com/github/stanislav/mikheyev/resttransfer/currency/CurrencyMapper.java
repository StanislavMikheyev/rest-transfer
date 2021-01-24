package com.github.stanislav.mikheyev.resttransfer.currency;

import org.mapstruct.Mapper;

@Mapper
public interface CurrencyMapper {
    CurrencyDto entityToDto(Currency dto);

    Iterable<CurrencyDto> entityToDto(Iterable<Currency> source);
}
