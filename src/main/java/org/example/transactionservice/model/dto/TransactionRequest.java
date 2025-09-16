package org.example.transactionservice.model.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class TransactionRequest {
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String customerId;
    private int page = 0;
    private int size = 20;
}
