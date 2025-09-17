package org.example.transactionservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.transactionservice.model.entity.Transaction;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@AllArgsConstructor
public class TransactionResponse {
    private List<Transaction> transactions;
    private int currentPage;
    private int totalPages;
    private long totalItems;

    public TransactionResponse(Page<Transaction> page) {
        this.transactions = page.getContent();
        this.currentPage = page.getNumber() + 1;
        this.totalPages = page.getTotalPages();
        this.totalItems = page.getTotalElements();
    }
}

