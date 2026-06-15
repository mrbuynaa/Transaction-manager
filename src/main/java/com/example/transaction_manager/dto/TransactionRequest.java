package com.example.transaction_manager.dto;

import java.math.BigDecimal;

public record TransactionRequest(
        Long instrumentId,
        Long buyOrderId,
        Long sellOrderId,
        Long buyUserId,
        Long sellUserId,
        BigDecimal amount,
        BigDecimal price
) {}
