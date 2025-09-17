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


@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository repository;
    private final CSVImporter csvImporter;

    @Transactional
    public TransactionResponse getTransactions(TransactionRequest request) {
        var specification = SpecsBuilder.buildSpecifications(request);
        Pageable pageable =  PageRequest.of(request.getPage(), request.getSize(),
                Sort.by("dateTime").descending());

        return new TransactionResponse(repository.findAll(specification, pageable));
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
