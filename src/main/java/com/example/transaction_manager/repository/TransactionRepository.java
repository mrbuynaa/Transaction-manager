package com.example.transaction_manager.repository;

import com.example.transaction_manager.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query("SELECT t FROM Transaction t WHERE t.buyUserId = :userId OR t.sellUserId = :userId")
    List<Transaction> findByUserId(@Param("userId") Long userId);
}
