package org.example.transactionservice.model.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class TransactionDto {
    private String transactionId;
    private String customerId;
    private String accountId;
    private BigDecimal amount;
    private LocalDateTime dateTime;
    private String type;
}
