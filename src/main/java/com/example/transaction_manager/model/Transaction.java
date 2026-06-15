package com.example.transaction_manager.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "TRANSACTION")
@Getter
@Setter
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "transaction_seq")
    @SequenceGenerator(name = "transaction_seq", sequenceName = "TRANSACTION_SEQ", allocationSize = 1)
    private Long id;

    @Column(name = "INSTRUMENT_ID", nullable = false)
    private Long instrumentId;

    @Column(name = "BUY_ORDER_ID", nullable = false)
    private Long buyOrderId;

    @Column(name = "SELL_ORDER_ID", nullable = false)
    private Long sellOrderId;

    @Column(name = "CREATED_DATE", nullable = false)
    private LocalDateTime createdDate;

    @Column(name = "AMOUNT", nullable = false, precision = 18, scale = 8)
    private BigDecimal amount;

    @Column(name = "PRICE", nullable = false, precision = 18, scale = 8)
    private BigDecimal price;

    @Column(name = "BUY_USER_ID", nullable = false)
    private Long buyUserId;

    @Column(name = "SELL_USER_ID", nullable = false)
    private Long sellUserId;
}
