package org.example.transactionservice.controller;

import lombok.RequiredArgsConstructor;
import org.example.transactionservice.model.dto.TransactionRequest;
import org.example.transactionservice.model.dto.TransactionResponse;
import org.example.transactionservice.service.TransactionService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService service;

    @GetMapping
    public TransactionResponse getTransactions(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
            @RequestParam(required = false) String customerId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        TransactionRequest request = new TransactionRequest();
        request.setStartDate(startDate);
        request.setEndDate(endDate);
        request.setCustomerId(customerId);
        request.setPage(page);
        request.setSize(size);

        return service.getTransactions(request);
    }
}
