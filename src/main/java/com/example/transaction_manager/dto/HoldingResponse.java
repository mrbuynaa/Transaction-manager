package com.example.transaction_manager.dto;

import com.example.transaction_manager.model.HoldingStatus;

import java.math.BigDecimal;

public record HoldingResponse(
        Long id,
        Long accountId,
        Long instrumentId,
        BigDecimal quantity,
        BigDecimal availableQuantity,
        BigDecimal holdQuantity,
        HoldingStatus status
) {}
