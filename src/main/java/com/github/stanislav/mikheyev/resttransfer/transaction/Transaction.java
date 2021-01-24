package com.github.stanislav.mikheyev.resttransfer.transaction;

import com.github.stanislav.mikheyev.resttransfer.account.Account;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JoinColumn(nullable = false)
    @ManyToOne
    private Account sourceAccount;

    @JoinColumn(nullable = false)
    @ManyToOne
    private Account targetAccount;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private BigDecimal sourceSnapshotAfterTransaction;

    @Column(nullable = false)
    private BigDecimal targetSnapshotAfterTransaction;
}
