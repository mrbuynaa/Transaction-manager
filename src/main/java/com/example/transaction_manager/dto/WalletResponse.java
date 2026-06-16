package com.example.transaction_manager.dto;

import com.example.transaction_manager.model.WalletStatus;

import java.math.BigDecimal;

public record WalletResponse(
        Long id,
        Long userId,
        Long instrumentId,
        BigDecimal balance,
        BigDecimal lockedBalance,
        WalletStatus status
) {}
