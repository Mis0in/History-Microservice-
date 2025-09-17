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

    public static Specification<Transaction> dateAfter(LocalDateTime date) {
        return dateBetween(date, null);
    }

    public static Specification<Transaction> dateBefore(LocalDateTime date) {
        return dateBetween(null, date);
    }

    public static Specification<Transaction> byCustomerId(String customerId) {
        return (root, query,  builder) ->
                builder.equal(root.get("customerId"), customerId);
    }
}
