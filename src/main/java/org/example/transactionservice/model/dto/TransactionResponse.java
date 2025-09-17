package org.example.transactionservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.transactionservice.model.entity.Transaction;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Response object containing transaction data and pagination metadata.
 * Wraps the results of transaction search operations.
 */
@Data
@AllArgsConstructor
public class TransactionResponse {
    /** List of transactions matching the query */
    private List<Transaction> transactions;

    /** Current page number (one-based) */
    private int currentPage;

    /** Total number of pages available */
    private int totalPages;

    /** Total number of items across all pages */
    private long totalItems;

    /**
     * Constructs a TransactionResponse from a Spring Data Page object.
     *
     * @param page the Page object containing transaction results and pagination info
     */
    public TransactionResponse(Page<Transaction> page) {
        this.transactions = page.getContent();
        this.currentPage = page.getNumber();
        this.totalPages = page.getTotalPages();
        this.totalItems = page.getTotalElements();
    }
}
