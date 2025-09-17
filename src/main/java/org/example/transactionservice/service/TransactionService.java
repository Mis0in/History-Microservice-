package org.example.transactionservice.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.transactionservice.model.dto.TransactionRequest;
import org.example.transactionservice.model.dto.TransactionResponse;
import org.example.transactionservice.model.entity.Transaction;
import org.example.transactionservice.model.entity.TransactionType;
import org.example.transactionservice.repository.TransactionRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import static org.example.transactionservice.repository.specs.TransactionSpecs.dateBetween;


@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository repository;
    private final CSVImporter csvImporter;

    @Transactional
    public TransactionResponse getTransactions(TransactionRequest request) {
        Page<Transaction> page = repository.findAll(
                dateBetween(request.getStartDate(), request.getEndDate()),
                PageRequest.of(request.getPage(), request.getSize()));

        return new TransactionResponse(
                page.getContent(),
                page.getNumber(),
                page.getTotalPages(),
                page.getTotalElements());
    }


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
