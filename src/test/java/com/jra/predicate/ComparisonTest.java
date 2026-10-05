package com.jra.predicate;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

import com.jra.model.Attribute;

class ComparisonTest {

    @Test
    void shouldCreateComparison() {
        Attribute department =
                new Attribute("department", String.class);

        Comparison comparison = new Comparison(
                department,
                Comparison.Operator.EQUAL,
                "CS"
        );

        assertSame(department, comparison.getAttribute());
        assertEquals(
                Comparison.Operator.EQUAL,
                comparison.getOperator()
        );
        assertEquals("CS", comparison.getValue());
    }

    @Test
    void shouldCreateNumericComparison() {
        Attribute year =
                new Attribute("year", Integer.class);

        Comparison comparison = new Comparison(
                year,
                Comparison.Operator.GREATER_THAN,
                2
        );

        assertSame(year, comparison.getAttribute());
        assertEquals(
                Comparison.Operator.GREATER_THAN,
                comparison.getOperator()
        );
        assertEquals(2, comparison.getValue());
    }

    @Test
    void shouldSupportAllOperators() {
        Attribute attribute =
                new Attribute("year", Integer.class);

        assertDoesNotThrow(() ->
                new Comparison(
                        attribute,
                        Comparison.Operator.EQUAL,
                        2
                )
        );

        assertDoesNotThrow(() ->
                new Comparison(
                        attribute,
                        Comparison.Operator.NOT_EQUAL,
                        2
                )
        );

        assertDoesNotThrow(() ->
                new Comparison(
                        attribute,
                        Comparison.Operator.LESS_THAN,
                        2
                )
        );

        assertDoesNotThrow(() ->
                new Comparison(
                        attribute,
                        Comparison.Operator.LESS_THAN_OR_EQUAL,
                        2
                )
        );

        assertDoesNotThrow(() ->
                new Comparison(
                        attribute,
                        Comparison.Operator.GREATER_THAN,
                        2
                )
        );

        assertDoesNotThrow(() ->
                new Comparison(
                        attribute,
                        Comparison.Operator.GREATER_THAN_OR_EQUAL,
                        2
                )
        );
    }

    @Test
    void shouldRejectNullAttribute() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Comparison(
                        null,
                        Comparison.Operator.EQUAL,
                        "CS"
                )
        );
    }

    @Test
    void shouldRejectNullOperator() {
        Attribute attribute =
                new Attribute("department", String.class);

        assertThrows(
                IllegalArgumentException.class,
                () -> new Comparison(
                        attribute,
                        null,
                        "CS"
                )
        );
    }

    @Test
    void shouldRejectInvalidValue() {
        Attribute year =
                new Attribute("year", Integer.class);

        assertThrows(
                IllegalArgumentException.class,
                () -> new Comparison(
                        year,
                        Comparison.Operator.GREATER_THAN,
                        "four"
                )
        );
    }

    @Test
    void shouldAcceptNullComparisonValue() {
        Attribute name =
                new Attribute("name", String.class);

        Comparison comparison = new Comparison(
                name,
                Comparison.Operator.EQUAL,
                null
        );

        assertNull(comparison.getValue());
    }
}