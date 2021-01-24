package com.github.stanislav.mikheyev.resttransfer.account;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountDto {
    private Integer id;
    private String name;
    private String currencyCode;
    private BigDecimal balance;
    private Boolean treasury;
}
