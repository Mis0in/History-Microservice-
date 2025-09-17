package org.example.transactionservice.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions_db", indexes = {
        @Index(name = "idx_transactions_date", columnList = "dateTime"),
        @Index(name = "idx_transactions_account", columnList = "accountId")
})
@Data
public class Transaction {
    @Id
    private String transactionId;

    @Column(nullable = false)
    private String customerId;

    @Column(nullable = false)
    private String accountId;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal amount;

    @Column(nullable = false)
    private LocalDateTime dateTime;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 7)
    private TransactionType type;
}

