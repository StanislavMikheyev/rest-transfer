package com.github.stanislav.mikheyev.resttransfer.transaction;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper
public interface TransactionMapper {
    @Mappings({
            @Mapping(target = "sourceAccountId", source = "sourceAccount.id"),
            @Mapping(target = "targetAccountId", source = "targetAccount.id")
    })
    TransactionDto entityToDto(Transaction transaction);

    Iterable<TransactionDto> entityToDto(Iterable<Transaction> transaction);
}
