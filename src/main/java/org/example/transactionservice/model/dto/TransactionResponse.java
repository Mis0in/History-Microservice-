package org.example.transactionservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.transactionservice.model.entity.Transaction;

import java.util.List;

@Data
@AllArgsConstructor
public class TransactionResponse {
    private List<Transaction> transactions;
    private int currentPage;
    private int totalPages;
    private long totalItems;
}

