package com.jra.predicate;

import com.jra.model.Attribute;

public class Comparison implements Predicate {

    public enum Operator {
        EQUAL,
        NOT_EQUAL,
        LESS_THAN,
        LESS_THAN_OR_EQUAL,
        GREATER_THAN,
        GREATER_THAN_OR_EQUAL
    }

    private final Attribute attribute;
    private final Operator operator;
    private final Object value;

    public Comparison(
            Attribute attribute,
            Operator operator,
            Object value
    ) {
        if (attribute == null) {
            throw new IllegalArgumentException(
                    "Attribute cannot be null"
            );
        }

        if (operator == null) {
            throw new IllegalArgumentException(
                    "Operator cannot be null"
            );
        }

        if (!attribute.isValidValue(value)) {
            throw new IllegalArgumentException(
                    "Invalid comparison value for attribute '"
                    + attribute.getName()
                    + "': "
                    + value
            );
        }

        this.attribute = attribute;
        this.operator = operator;
        this.value = value;
    }

    public Attribute getAttribute() {
        return attribute;
    }

    public Operator getOperator() {
        return operator;
    }

    public Object getValue() {
        return value;
    }
}