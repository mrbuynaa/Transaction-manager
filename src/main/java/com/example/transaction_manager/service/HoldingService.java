package com.example.transaction_manager.service;

import com.example.transaction_manager.dto.HoldingResponse;
import com.example.transaction_manager.dto.LockQuantityRequest;
import com.example.transaction_manager.model.Holding;
import com.example.transaction_manager.model.HoldingStatus;
import com.example.transaction_manager.repository.HoldingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HoldingService {

    private final HoldingRepository holdingRepository;

    public List<HoldingResponse> getByAccountId(Long accountId) {
        return holdingRepository.findByAccountId(accountId).stream()
                .map(this::toResponse)
                .toList();
    }

    public HoldingResponse getByAccountIdAndInstrumentId(Long accountId, Long instrumentId) {
        return toResponse(findHoldingOrThrow(accountId, instrumentId));
    }

    @Transactional
    public HoldingResponse lockQuantity(LockQuantityRequest request) {
        BigDecimal amount = request.amount();
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Amount must be positive");
        }

        int updated = holdingRepository.lockQuantity(
                request.accountId(), request.instrumentId(), amount, HoldingStatus.ACTIVE);
        if (updated == 0) {
            Holding holding = findHoldingOrThrow(request.accountId(), request.instrumentId());
            if (holding.getStatus() != HoldingStatus.ACTIVE) {
                throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY,
                        "Cannot lock quantity on a " + holding.getStatus() + " holding");
            }
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Insufficient quantity");
        }

        return toResponse(findHoldingOrThrow(request.accountId(), request.instrumentId()));
    }

    @Transactional
    public HoldingResponse releaseLock(LockQuantityRequest request) {
        BigDecimal amount = request.amount();
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Amount must be positive");
        }

        int updated = holdingRepository.releaseHoldQuantity(request.accountId(), request.instrumentId(), amount);
        if (updated == 0) {
            findHoldingOrThrow(request.accountId(), request.instrumentId());
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Insufficient locked quantity");
        }

        return toResponse(findHoldingOrThrow(request.accountId(), request.instrumentId()));
    }

    private Holding findHoldingOrThrow(Long accountId, Long instrumentId) {
        return holdingRepository.findByAccountIdAndInstrumentId(accountId, instrumentId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Holding not found for accountId=" + accountId + " instrumentId=" + instrumentId));
    }

    private HoldingResponse toResponse(Holding holding) {
        return new HoldingResponse(holding.getId(), holding.getAccountId(), holding.getInstrumentId(),
                holding.getQuantity(), holding.available(), holding.getHoldQuantity(), holding.getStatus());
    }
}
