package com.example.transaction_manager.controller;

import com.example.transaction_manager.dto.HoldingResponse;
import com.example.transaction_manager.dto.LockQuantityRequest;
import com.example.transaction_manager.service.HoldingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/holdings")
@RequiredArgsConstructor
public class HoldingController {

    private final HoldingService holdingService;

    @GetMapping("/account/{accountId}")
    public List<HoldingResponse> getByAccountId(@PathVariable Long accountId) {
        return holdingService.getByAccountId(accountId);
    }

    @GetMapping("/account/{accountId}/instrument/{instrumentId}")
    public HoldingResponse getByAccountIdAndInstrumentId(
            @PathVariable Long accountId,
            @PathVariable Long instrumentId) {
        return holdingService.getByAccountIdAndInstrumentId(accountId, instrumentId);
    }

    @GetMapping("/balance")
    public HoldingResponse getBalance(
            @RequestParam Long accountId,
            @RequestParam Long instrumentId) {
        return holdingService.getByAccountIdAndInstrumentId(accountId, instrumentId);
    }

    @PostMapping("/lock")
    public HoldingResponse lockQuantity(@RequestBody LockQuantityRequest request) {
        return holdingService.lockQuantity(request);
    }

    @PostMapping("/release")
    public HoldingResponse releaseLock(@RequestBody LockQuantityRequest request) {
        return holdingService.releaseLock(request);
    }
}
