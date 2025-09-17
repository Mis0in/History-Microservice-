package org.example.transactionservice.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Entity class representing a financial transaction.
 * Maps to the transactions_db table in the database.
 */
@Entity
@Table(name = "transactions_db", indexes = {
        @Index(name = "idx_transactions_date", columnList = "dateTime"),
        @Index(name = "idx_transactions_account", columnList = "accountId")
})
@Data
public class Transaction {
    /** Unique transaction identifier */
    @Id
    private String transactionId;

    /** Customer identifier associated with the transaction */
    @Column(nullable = false)
    private String customerId;

    /** Account identifier associated with the transaction */
    @Column(nullable = false)
    private String accountId;

    /** Transaction amount with precision for financial calculations */
    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal amount;

    /** Date and time when the transaction occurred */
    @Column(nullable = false)
    private LocalDateTime dateTime;

    /** Type of transaction (income or outcome) */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 7)
    private TransactionType type;
}