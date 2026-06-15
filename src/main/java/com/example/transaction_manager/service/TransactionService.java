package com.example.transaction_manager.service;

import com.example.transaction_manager.dto.TransactionRequest;
import com.example.transaction_manager.dto.TransactionResponse;
import com.example.transaction_manager.model.Transaction;
import com.example.transaction_manager.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;

    public List<TransactionResponse> getByUserId(Long userId) {
        return transactionRepository.findByUserId(userId).stream()
                .map(t -> new TransactionResponse(
                        t.getId(),
                        t.getInstrumentId(),
                        t.getBuyOrderId(),
                        t.getSellOrderId(),
                        t.getBuyUserId(),
                        t.getSellUserId(),
                        t.getAmount(),
                        t.getPrice(),
                        t.getCreatedDate()
                ))
                .toList();
    }

    public TransactionResponse create(TransactionRequest request) {
        Transaction transaction = new Transaction();
        transaction.setInstrumentId(request.instrumentId());
        transaction.setBuyOrderId(request.buyOrderId());
        transaction.setSellOrderId(request.sellOrderId());
        transaction.setBuyUserId(request.buyUserId());
        transaction.setSellUserId(request.sellUserId());
        transaction.setAmount(request.amount());
        transaction.setPrice(request.price());
        transaction.setCreatedDate(LocalDateTime.now());

        Transaction saved = transactionRepository.save(transaction);

        return new TransactionResponse(
                saved.getId(),
                saved.getInstrumentId(),
                saved.getBuyOrderId(),
                saved.getSellOrderId(),
                saved.getBuyUserId(),
                saved.getSellUserId(),
                saved.getAmount(),
                saved.getPrice(),
                saved.getCreatedDate()
        );
    }
}
