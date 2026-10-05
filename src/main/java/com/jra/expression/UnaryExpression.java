package com.jra.expression;

public abstract class UnaryExpression implements Expression {

    private final Expression child;

    protected UnaryExpression(Expression child) {
        if (child == null) {
            throw new IllegalArgumentException(
                    "Child expression cannot be null"
            );
        }

        this.child = child;
    }

    public Expression getChild() {
        return child;
    }
}