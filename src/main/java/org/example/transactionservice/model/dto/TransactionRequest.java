package org.example.transactionservice.model.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * Data transfer object for transaction query parameters.
 * Contains filtering criteria and pagination settings for transaction searches.
 */
@Data
public class TransactionRequest {
    /** Start date for filtering transactions (inclusive) */
    private LocalDateTime startDate;

    /** End date for filtering transactions (inclusive) */
    private LocalDateTime endDate;

    /** Customer identifier for filtering */
    private String customerId;

    /** Account identifier for filtering */
    private String accountId;

    /** Page number for pagination (zero-based) */
    private int page = 0;

    /** Number of items per page */
    private int size = 20;
}
