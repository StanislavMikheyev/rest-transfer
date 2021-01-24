package com.github.stanislav.mikheyev.resttransfer.transaction;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionDto {
    private Integer id;
    private Integer sourceAccountId;
    private Integer targetAccountId;
    private BigDecimal sourceSnapshotAfterTransaction;
    private BigDecimal targetSnapshotAfterTransaction;
    private BigDecimal amount;
}
