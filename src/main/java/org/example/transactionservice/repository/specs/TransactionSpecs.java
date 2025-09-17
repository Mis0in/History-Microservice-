package org.example.transactionservice.repository.specs;

import org.example.transactionservice.model.entity.Transaction;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Contains static factory methods for creating JPA Specifications for {@link Transaction} entities.
 * Each method returns a {@link Specification} for a specific filter criterion.
 */
public class TransactionSpecs {

    /**
     * Creates a specification for transactions occurring on or after the given date.
     *
     * @param date the minimum date for filtering (inclusive)
     * @return Specification for date range filtering
     * @throws NullPointerException if date is null
     */
    public static Specification<Transaction> dateAfter(LocalDateTime date) {
        Objects.requireNonNull(date, "date must not be null");
        return (root, query, builder) ->
                builder.greaterThanOrEqualTo(root.get("dateTime"), date);
    }

    /**
     * Creates a specification for transactions occurring on or before the given date.
     *
     * @param date the maximum date for filtering (inclusive)
     * @return Specification for date range filtering
     * @throws NullPointerException if date is null
     */
    public static Specification<Transaction> dateBefore(LocalDateTime date) {
        Objects.requireNonNull(date, "date must not be null");
        return (root, query, builder) ->
                builder.lessThanOrEqualTo(root.get("dateTime"), date);
    }

    /**
     * Creates a specification for transactions matching the given customer ID.
     *
     * @param customerId the customer identifier to filter by
     * @return Specification for customer ID filtering
     * @throws NullPointerException if customerId is null
     */
    public static Specification<Transaction> byCustomerId(String customerId) {
        Objects.requireNonNull(customerId, "customerId must not be null");
        return (root, query,  builder) ->
                builder.equal(root.get("customerId"), customerId);
    }

    /**
     * Creates a specification for transactions matching the given account ID.
     *
     * @param accountId the account identifier to filter by
     * @return Specification for account ID filtering
     * @throws NullPointerException if accountId is null
     */
    public static Specification<Transaction> byAccountId(String accountId) {
        Objects.requireNonNull(accountId, "accountId must not be null");
        return (root, query,  builder) ->
                builder.equal(root.get("accountId"), accountId);
    }
}