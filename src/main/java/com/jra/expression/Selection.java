package com.jra.expression;

import com.jra.predicate.Predicate;
import com.jra.model.Schema;

public class Selection extends UnaryExpression {

    private final Predicate predicate;

    public Selection(
            Expression child,
            Predicate predicate
    ) {
        super(child);

        if (predicate == null) {
            throw new IllegalArgumentException(
                    "Predicate cannot be null"
            );
        }

        this.predicate = predicate;
    }

    public Predicate getPredicate() {
        return predicate;
    }

    @Override
    public Schema getSchema() {
        return getChild().getSchema();
    }
}