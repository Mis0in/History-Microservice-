package org.example.transactionservice.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.transactionservice.model.dto.TransactionRequest;
import org.example.transactionservice.model.dto.TransactionResponse;
import org.example.transactionservice.model.entity.TransactionType;
import org.example.transactionservice.repository.TransactionRepository;
import org.example.transactionservice.repository.specs.SpecsBuilder;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 * Service class for transaction operations.
 * Handles business logic for retrieving transactions and initializing database data.
 */
@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository repository;
    private final CSVImporter csvImporter;

    /**
     * Retrieves transactions based on filter criteria with pagination and sorting.
     *
     * @param request the {@link TransactionRequest} containing filter parameters and pagination settings
     * @return {@link TransactionResponse} with filtered results and pagination metadata
     */
    @Transactional
    public TransactionResponse getTransactions(TransactionRequest request) {
        var specification = SpecsBuilder.buildSpecifications(request);
        Pageable pageable =  PageRequest.of(request.getPage(), request.getSize(),
                Sort.by("dateTime").descending());

        return new TransactionResponse(repository.findAll(specification, pageable));
    }

    /**
     * Initializes the database with sample data from CSV files on application startup.
     * Only imports data if the database is empty.
     *
     * @throws RuntimeException if CSV import fails
     */
    @EventListener(ApplicationReadyEvent.class)
    @Transactional
    public void initDatabase() {
        if (repository.count() == 0) {
            try {
                csvImporter.importCsv("incomes.csv", TransactionType.INCOME);
                csvImporter.importCsv("outcomes.csv", TransactionType.OUTCOME);
            } catch (Exception e) {
                throw new RuntimeException("Database initialization failed: " + e.getMessage(), e);
            }
        }
    }
}