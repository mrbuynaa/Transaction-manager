package com.example.transaction_manager.repository;

import com.example.transaction_manager.model.Holding;
import com.example.transaction_manager.model.HoldingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface HoldingRepository extends JpaRepository<Holding, Long> {

    List<Holding> findByAccountId(Long accountId);

    Optional<Holding> findByAccountIdAndInstrumentId(Long accountId, Long instrumentId);

    @Modifying
    @Query("update Holding h set h.holdQuantity = h.holdQuantity + :amount " +
            "where h.accountId = :accountId and h.instrumentId = :instrumentId and h.status = :status " +
            "and (h.quantity - h.holdQuantity) >= :amount")
    int lockQuantity(@Param("accountId") Long accountId, @Param("instrumentId") Long instrumentId,
                      @Param("amount") BigDecimal amount, @Param("status") HoldingStatus status);

    @Modifying
    @Query("update Holding h set h.holdQuantity = h.holdQuantity - :amount " +
            "where h.accountId = :accountId and h.instrumentId = :instrumentId and h.holdQuantity >= :amount")
    int releaseHoldQuantity(@Param("accountId") Long accountId, @Param("instrumentId") Long instrumentId,
                             @Param("amount") BigDecimal amount);
}
