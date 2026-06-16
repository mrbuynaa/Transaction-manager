package com.example.transaction_manager.service;

import com.example.transaction_manager.dto.LockBalanceRequest;
import com.example.transaction_manager.dto.WalletResponse;
import com.example.transaction_manager.model.Wallet;
import com.example.transaction_manager.model.WalletStatus;
import com.example.transaction_manager.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WalletService {

    private final WalletRepository walletRepository;

    public List<WalletResponse> getByUserId(Long userId) {
        return walletRepository.findByUserId(userId).stream()
                .map(w -> new WalletResponse(w.getId(), w.getUserId(), w.getInstrumentId(),
                        w.getBalance(), w.getLockedBalance(), w.getStatus()))
                .toList();
    }

    public WalletResponse getByUserIdAndInstrumentId(Long userId, Long instrumentId) {
        return walletRepository.findByUserIdAndInstrumentId(userId, instrumentId)
                .map(w -> new WalletResponse(w.getId(), w.getUserId(), w.getInstrumentId(),
                        w.getBalance(), w.getLockedBalance(), w.getStatus()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Wallet not found for userId=" + userId + " instrumentId=" + instrumentId));
    }

    @Transactional
    public WalletResponse lockBalance(LockBalanceRequest request) {
        Wallet wallet = walletRepository.findByUserIdAndInstrumentId(request.userId(), request.instrumentId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Wallet not found for userId=" + request.userId() + " instrumentId=" + request.instrumentId()));

        if (wallet.getStatus() != WalletStatus.ACTIVE) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY,
                    "Cannot lock balance on a " + wallet.getStatus() + " wallet");
        }

        BigDecimal amount = request.amount();
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Amount must be positive");
        }
        if (wallet.getBalance().compareTo(amount) < 0) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Insufficient balance");
        }

        wallet.setBalance(wallet.getBalance().subtract(amount));
        wallet.setLockedBalance(wallet.getLockedBalance().add(amount));
        Wallet saved = walletRepository.save(wallet);

        return new WalletResponse(saved.getId(), saved.getUserId(), saved.getInstrumentId(),
                saved.getBalance(), saved.getLockedBalance(), saved.getStatus());
    }
}
