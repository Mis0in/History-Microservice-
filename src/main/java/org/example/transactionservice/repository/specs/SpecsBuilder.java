package org.example.transactionservice.repository.specs;

import org.example.transactionservice.model.dto.TransactionRequest;
import org.example.transactionservice.model.entity.Transaction;
import org.springframework.data.jpa.domain.Specification;

import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * Utility class for building JPA Specifications from {@link TransactionRequest} objects.
 * Provides a fluent API for constructing dynamic queries with optional filters.
 */
public class SpecsBuilder {
    /** Idempotent specification that always evaluates to true (no filtering) */
    public final static Specification<Transaction> IDEMPOTENT_SPEC =
            (r,q,b) -> b.conjunction();

    /**
     * Builds a specification from a getter and specification method.
     * Returns null if the getter returns null, preventing unnecessary filtering.
     *
     * @param <T> the type of the filter value
     * @param getter supplier function to retrieve the filter value
     * @param specMethod function to create specification from the filter value
     * @return Specification if value is present, null otherwise
     */
    private static <T> Specification<Transaction> buildSpec(
            Supplier<T> getter,
            Function<T, Specification<Transaction>> specMethod) {
        T got = getter.get();
        if (got == null) {
            return null;
        }
        return specMethod.apply(getter.get());
    }

    /**
     * Builds a composite specification from {@link TransactionRequest} parameters.
     * Combines individual specifications with AND logic
     * returns 'always true' specification if no filters are provided.
     *
     * @param req the TransactionRequest containing filter criteria
     * @return Composite Specification combining all provided filters
     */
    public static Specification<Transaction> buildSpecifications(TransactionRequest req) {
        return Stream.of(
                        buildSpec(req::getStartDate, TransactionSpecs::dateAfter),
                        buildSpec(req::getEndDate, TransactionSpecs::dateBefore),
                        buildSpec(req::getCustomerId, TransactionSpecs::byCustomerId),
                        buildSpec(req::getAccountId, TransactionSpecs::byAccountId)
                ).filter(Objects::nonNull)
                .reduce(Specification::and)
                .orElse(IDEMPOTENT_SPEC);
    }
}