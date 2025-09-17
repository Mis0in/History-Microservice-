package org.example.transactionservice.service;

import com.opencsv.CSVReader;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.transactionservice.model.entity.Transaction;
import org.example.transactionservice.model.entity.TransactionType;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Component for importing transaction data from CSV files.
 * Handles batch processing of large CSV files with efficient memory management.
 */
@Component
@RequiredArgsConstructor
public class CSVImporter {

    /** Batch size for database flush operations */
    public static final int READ_LIMIT = 1000;

    /** DateTime formatter for parsing CSV date strings */
    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    @PersistenceContext
    private EntityManager em;

    /**
     * Imports transactions from a CSV file and persists them to the database.
     * Uses batch processing for memory efficiency with large files.
     *
     * @param filename the name of the CSV file to import
     * @param type the transaction type (INCOME/OUTCOME) for all records in the file
     * @throws RuntimeException if file reading or parsing fails
     */
    @Transactional
    public void importCsv(String filename, TransactionType type) {
        try (CSVReader reader = new CSVReader(new FileReader(filename))) {
            int counter = 0;

            reader.skip(1); // Skip header row

            String[] line;
            while ((line = reader.readNext()) != null) {
                Transaction t = new Transaction();
                t.setTransactionId(line[0]);
                t.setCustomerId(line[1]);
                t.setAccountId(line[2]);
                t.setAmount(new BigDecimal(line[3]));
                t.setDateTime(LocalDateTime.parse(line[4], FORMATTER));
                t.setType(type);

                em.persist(t);

                // Batch processing for memory efficiency
                if (++counter % READ_LIMIT == 0) {
                    em.flush();
                    em.clear();
                }
            }
            em.flush(); // Final flush
        } catch (Exception e) {
            throw new RuntimeException("CSV import failed: " + e.getMessage(), e);
        }
    }
}