package com.example.transaction_manager.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "HOLDING",
        uniqueConstraints = @UniqueConstraint(
                name = "UK_HOLDING_ACCOUNT_INSTRUMENT",
                columnNames = {"ACCOUNT_ID", "INSTRUMENT_ID"}))
@Getter
@Setter
public class Holding {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "holding_seq")
    @SequenceGenerator(name = "holding_seq", sequenceName = "HOLDING_SEQ", allocationSize = 1)
    private Long id;

    @Column(name = "ACCOUNT_ID", nullable = false)
    private Long accountId;

    @Column(name = "INSTRUMENT_ID", nullable = false)
    private Long instrumentId;

    @Column(name = "QUANTITY", nullable = false, precision = 24, scale = 8)
    private BigDecimal quantity;

    @Column(name = "HOLD_QUANTITY", nullable = false, precision = 24, scale = 8)
    private BigDecimal holdQuantity;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS", nullable = false, length = 10)
    private HoldingStatus status;

    public BigDecimal available() {
        return quantity.subtract(holdQuantity);
    }
}
