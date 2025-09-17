package org.example.transactionservice.repository;

import org.example.transactionservice.model.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


/**
 * Repository interface for {@link Transaction} entities.
 */
public interface TransactionRepository extends
        JpaRepository<Transaction, String>,
        JpaSpecificationExecutor<Transaction> {
}