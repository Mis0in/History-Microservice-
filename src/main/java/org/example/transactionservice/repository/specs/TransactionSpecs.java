package org.example.transactionservice.repository.specs;

import org.example.transactionservice.model.entity.Transaction;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.util.Objects;

public class TransactionSpecs {

    public static Specification<Transaction> dateAfter(LocalDateTime date) {
        Objects.requireNonNull(date, "date must not be null");
        return (root, query, builder) ->
                builder.greaterThanOrEqualTo(root.get("dateTime"), date);
    }

    public static Specification<Transaction> dateBefore(LocalDateTime date) {
        Objects.requireNonNull(date, "date must not be null");
        return (root, query, builder) ->
                builder.lessThanOrEqualTo(root.get("dateTime"), date);
    }

    public static Specification<Transaction> byCustomerId(String customerId) {
        Objects.requireNonNull(customerId, "customerId must not be null");
        return (root, query,  builder) ->
                builder.equal(root.get("customerId"), customerId);
    }

    public static Specification<Transaction> byAccountId(String accountId) {
        Objects.requireNonNull(accountId, "accountId must not be null");
        return (root, query,  builder) ->
                builder.equal(root.get("accountId"), accountId);
    }
}
