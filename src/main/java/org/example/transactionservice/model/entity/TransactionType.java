package org.example.transactionservice.model.entity;

/**
 * Enumeration representing transaction types.
 * Defines the possible categories for financial transactions.
 */
public enum TransactionType {
    /** Incoming funds or credits */
    INCOME,

    /** Outgoing funds or debits */
    OUTCOME
}