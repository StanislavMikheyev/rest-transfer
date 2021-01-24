package com.github.stanislav.mikheyev.resttransfer.currency;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CurrencyRepository extends CrudRepository<Currency, Integer> {
    Optional<Currency> findByCode(String code);
}
