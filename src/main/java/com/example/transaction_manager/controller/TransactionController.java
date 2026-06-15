package com.example.transaction_manager.controller;

import com.example.transaction_manager.dto.TransactionRequest;
import com.example.transaction_manager.dto.TransactionResponse;
import com.example.transaction_manager.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @GetMapping("/user/{userId}")
    public List<TransactionResponse> getByUserId(@PathVariable Long userId) {
        return transactionService.getByUserId(userId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TransactionResponse create(@RequestBody TransactionRequest request) {
        return transactionService.create(request);
    }
}
