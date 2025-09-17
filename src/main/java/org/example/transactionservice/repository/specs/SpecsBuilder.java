package org.example.transactionservice.repository.specs;

import org.example.transactionservice.model.dto.TransactionRequest;
import org.example.transactionservice.model.entity.Transaction;
import org.springframework.data.jpa.domain.Specification;

import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class SpecsBuilder {
    private final static Specification<Transaction> idempotentSpec =
            (r,q,b) -> b.conjunction();

    private static <T> Specification<Transaction> buildSpec(Supplier<T> getter, Function<T, Specification<Transaction>> specMethod) {
        T got = getter.get();
        if (got == null) {
            return null;
        }

        return specMethod.apply(getter.get());
    }

    public static Specification<Transaction> buildSpecifications(TransactionRequest req) {
        return Stream.of(
                buildSpec(req::getStartDate, TransactionSpecs::dateAfter),
                buildSpec(req::getEndDate, TransactionSpecs::dateBefore),
                buildSpec(req::getCustomerId, TransactionSpecs::byCustomerId)
                ).filter(Objects::nonNull).reduce(Specification::and).orElse(idempotentSpec);
    }
}
