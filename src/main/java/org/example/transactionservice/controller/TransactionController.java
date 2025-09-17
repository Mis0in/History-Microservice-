package org.example.transactionservice.controller;

import lombok.RequiredArgsConstructor;
import org.example.transactionservice.model.dto.TransactionRequest;
import org.example.transactionservice.model.dto.TransactionResponse;
import org.example.transactionservice.service.TransactionService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * REST controller for transaction operations.
 * Provides endpoints for retrieving transaction data with filtering and pagination.
 */
@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService service;

    /**
     * Retrieves transactions with optional filtering and pagination.
     *
     * @param startDate optional start date for filtering transactions (ISO format: yyyy-MM-dd'T'HH:mm:ss)
     * @param endDate optional end date for filtering transactions (ISO format: yyyy-MM-dd'T'HH:mm:ss)
     * @param customerId optional customer identifier for filtering
     * @param accountId optional account identifier for filtering
     * @param page page number for pagination (default: 0)
     * @param size number of items per page (default: 20)
     * @return TransactionResponse containing filtered transactions and pagination metadata
     */
    @GetMapping
    public TransactionResponse getTransactions(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
            @RequestParam(required = false) String customerId,
            @RequestParam(required = false) String accountId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        TransactionRequest request = new TransactionRequest();
        request.setStartDate(startDate);
        request.setEndDate(endDate);
        request.setCustomerId(customerId);
        request.setAccountId(accountId);
        request.setPage(page);
        request.setSize(size);

        return service.getTransactions(request);
    }
}
