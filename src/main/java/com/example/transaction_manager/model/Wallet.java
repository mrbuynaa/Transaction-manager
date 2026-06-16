package com.example.transaction_manager.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "WALLET")
@Getter
@Setter
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "wallet_seq")
    @SequenceGenerator(name = "wallet_seq", sequenceName = "WALLET_SEQ", allocationSize = 1)
    private Long id;

    @Column(name = "USER_ID", nullable = false)
    private Long userId;

    @Column(name = "INSTRUMENT_ID", nullable = false)
    private Long instrumentId;

    @Column(name = "BALANCE", nullable = false, precision = 18, scale = 8)
    private BigDecimal balance;

    @Column(name = "LOCKED_BALANCE", nullable = false, precision = 18, scale = 8)
    private BigDecimal lockedBalance;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS", nullable = false, length = 10)
    private WalletStatus status;
}
