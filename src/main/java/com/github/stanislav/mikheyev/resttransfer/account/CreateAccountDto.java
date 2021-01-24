package com.github.stanislav.mikheyev.resttransfer.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateAccountDto {
    @JsonProperty(required = true)
    private String name;
    @JsonProperty(required = true)
    private Boolean treasury;
    @JsonProperty(required = true)
    private String currencyCode;
    private BigDecimal balance;
}
