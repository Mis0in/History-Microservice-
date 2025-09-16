package org.example.transactionservice.repository.specs;

import org.example.transactionservice.model.entity.Transaction;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class TransactionSpecs {

    final static LocalDateTime minDate = LocalDateTime.of(LocalDate.of(1000, 10, 10), LocalTime.of(10, 10));

    public static Specification<Transaction> dateBetween(LocalDateTime startDate, LocalDateTime endDate) {
        if (startDate == null) {
            startDate = minDate;
        }
        if (endDate == null) {
            endDate = LocalDateTime.now();
        }

        final var finalStartDate = startDate;
        final var finalEndDate = endDate;
        return (root, query, builder) ->
                builder.between(root.get("dateTime"), finalStartDate, finalEndDate);
    }
}
