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

@Component
@RequiredArgsConstructor
public class CSVImporter {

    public static final int READ_LIMIT = 1000;
    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void importCsv(String filename, TransactionType type) {
        try (CSVReader reader = new CSVReader(new FileReader(filename))) {
            int counter = 0;

            reader.skip(1);

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

                if (++counter % READ_LIMIT == 0) {
                    em.flush();
                    em.clear();
                }
            }
            em.flush();
        } catch (Exception e) {
            throw new RuntimeException("CSV import failed: " + e.getMessage(), e);
        }
    }
}
