package com.example.transaction_manager.dto;

import java.math.BigDecimal;

public record LockBalanceRequest(Long userId, Long instrumentId, BigDecimal amount) {}
