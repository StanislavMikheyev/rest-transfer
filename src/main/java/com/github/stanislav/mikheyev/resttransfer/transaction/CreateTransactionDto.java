package com.github.stanislav.mikheyev.resttransfer.transaction;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateTransactionDto {
    @JsonProperty(required = true)
    private Integer sourceAccountId;
    @JsonProperty(required = true)
    private Integer targetAccountId;
    @JsonProperty(required = true)
    private BigDecimal amount;
}
