package com.example.transaction_manager.dto;

import java.math.BigDecimal;

public record LockQuantityRequest(Long accountId, Long instrumentId, BigDecimal amount) {}
