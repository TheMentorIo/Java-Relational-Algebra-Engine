package com.jra.expression;

public abstract class BinaryExpression implements Expression {

    private final Expression left;
    private final Expression right;

    protected BinaryExpression(
            Expression left,
            Expression right
    ) {
        if (left == null) {
            throw new IllegalArgumentException(
                    "Left expression cannot be null"
            );
        }

        if (right == null) {
            throw new IllegalArgumentException(
                    "Right expression cannot be null"
            );
        }

        this.left = left;
        this.right = right;
    }

    public Expression getLeft() {
        return left;
    }

    public Expression getRight() {
        return right;
    }
}