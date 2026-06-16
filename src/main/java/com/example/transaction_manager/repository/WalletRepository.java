package com.example.transaction_manager.repository;

import com.example.transaction_manager.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WalletRepository extends JpaRepository<Wallet, Long> {

    List<Wallet> findByUserId(Long userId);

    Optional<Wallet> findByUserIdAndInstrumentId(Long userId, Long instrumentId);
}
