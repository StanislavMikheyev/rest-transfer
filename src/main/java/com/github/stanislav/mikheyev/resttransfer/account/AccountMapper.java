package com.github.stanislav.mikheyev.resttransfer.account;

import com.github.stanislav.mikheyev.resttransfer.currency.CurrencyMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(uses = {CurrencyMapper.class})
public interface AccountMapper {
    Iterable<AccountDto> entityToDto(Iterable<Account> accounts);

    @Mappings({
            @Mapping(target = "currencyCode", source = "currency.code")
    })
    AccountDto entityToDto(Account account);
}
