package org.example.transactionservice.model.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class TransactionResponse {
    private List<TransactionDto> transactions;
    private int currentPage;
    private int totalPages;
    private long totalItems;
}

