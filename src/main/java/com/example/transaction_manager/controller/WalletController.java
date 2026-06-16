package com.example.transaction_manager.controller;

import com.example.transaction_manager.dto.LockBalanceRequest;
import com.example.transaction_manager.dto.WalletResponse;
import com.example.transaction_manager.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/wallets")
@RequiredArgsConstructor
public class WalletController {

    private final WalletService walletService;

    @GetMapping("/user/{userId}")
    public List<WalletResponse> getByUserId(@PathVariable Long userId) {
        return walletService.getByUserId(userId);
    }

    @GetMapping("/user/{userId}/instrument/{instrumentId}")
    public WalletResponse getByUserIdAndInstrumentId(
            @PathVariable Long userId,
            @PathVariable Long instrumentId) {
        return walletService.getByUserIdAndInstrumentId(userId, instrumentId);
    }

    @GetMapping("/balance")
    public WalletResponse getBalance(
            @RequestParam Long userId,
            @RequestParam Long instrumentId) {
        return walletService.getByUserIdAndInstrumentId(userId, instrumentId);
    }

    @PostMapping("/lock")
    public WalletResponse lockBalance(@RequestBody LockBalanceRequest request) {
        return walletService.lockBalance(request);
    }
}
