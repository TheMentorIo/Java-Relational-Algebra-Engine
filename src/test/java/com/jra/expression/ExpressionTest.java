package com.jra.expression;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

import com.jra.model.Attribute;
import com.jra.model.Schema;

class ExpressionTest {

    private Schema createSchema() {
        return new Schema(
                List.of(
                        new Attribute("id", Integer.class)
                )
        );
    }

    @Test
    void shouldCreateSimpleExpression() {
        Schema schema = createSchema();

        TestExpression expression = new TestExpression(schema);

        assertSame(schema, expression.getSchema());
    }

    @Test
    void shouldCreateUnaryExpression() {
        Schema schema = createSchema();

        TestExpression child = new TestExpression(schema);

        TestUnaryExpression expression =
                new TestUnaryExpression(child);

        assertSame(child, expression.getChild());
        assertSame(schema, expression.getSchema());
    }

    @Test
    void shouldRejectNullUnaryChild() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new TestUnaryExpression(null)
        );
    }

    @Test
    void shouldCreateBinaryExpression() {
        Schema leftSchema = createSchema();
        Schema rightSchema = createSchema();

        TestExpression left =
                new TestExpression(leftSchema);

        TestExpression right =
                new TestExpression(rightSchema);

        TestBinaryExpression expression =
                new TestBinaryExpression(left, right);

        assertSame(left, expression.getLeft());
        assertSame(right, expression.getRight());
    }

    @Test
    void shouldRejectNullLeftExpression() {
        TestExpression right =
                new TestExpression(createSchema());

        assertThrows(
                IllegalArgumentException.class,
                () -> new TestBinaryExpression(null, right)
        );
    }

    @Test
    void shouldRejectNullRightExpression() {
        TestExpression left =
                new TestExpression(createSchema());

        assertThrows(
                IllegalArgumentException.class,
                () -> new TestBinaryExpression(left, null)
        );
    }

    private static class TestExpression implements Expression {

        private final Schema schema;

        TestExpression(Schema schema) {
            this.schema = schema;
        }

        @Override
        public Schema getSchema() {
            return schema;
        }
    }

    private static class TestUnaryExpression
            extends UnaryExpression {

        TestUnaryExpression(Expression child) {
            super(child);
        }

        @Override
        public Schema getSchema() {
            return getChild().getSchema();
        }
    }

    private static class TestBinaryExpression
            extends BinaryExpression {

        TestBinaryExpression(
                Expression left,
                Expression right
        ) {
            super(left, right);
        }

        @Override
        public Schema getSchema() {
            return getLeft().getSchema();
        }
    }
}