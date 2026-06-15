package com.example.transaction_manager.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionResponse(
        Long id,
        Long instrumentId,
        Long buyOrderId,
        Long sellOrderId,
        Long buyUserId,
        Long sellUserId,
        BigDecimal amount,
        BigDecimal price,
        LocalDateTime createdDate
) {}
