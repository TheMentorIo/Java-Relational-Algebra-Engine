package com.jra.model;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

class TupleTest {

    private Schema createStudentSchema() {
        return new Schema(
                Arrays.asList(
                        new Attribute("student_id", Integer.class),
                        new Attribute("name", String.class),
                        new Attribute("year", Integer.class)
                )
        );
    }

    @Test
    void shouldCreateTuple() {
        Schema schema = createStudentSchema();

        Tuple tuple = new Tuple(
                schema,
                Arrays.asList(1, "Ahmed", 4)
        );

        assertEquals(3, tuple.size());
        assertEquals(1, tuple.getValue(0));
        assertEquals("Ahmed", tuple.getValue(1));
        assertEquals(4, tuple.getValue(2));
    }

    @Test
    void shouldGetValueByAttributeName() {
        Schema schema = createStudentSchema();

        Tuple tuple = new Tuple(
                schema,
                Arrays.asList(1, "Ahmed", 4)
        );

        assertEquals(1, tuple.getValue("student_id"));
        assertEquals("Ahmed", tuple.getValue("name"));
        assertEquals(4, tuple.getValue("year"));
    }

    @Test
    void shouldRejectUnknownAttributeName() {
        Schema schema = createStudentSchema();

        Tuple tuple = new Tuple(
                schema,
                Arrays.asList(1, "Ahmed", 4)
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> tuple.getValue("department")
        );
    }

    @Test
    void shouldRejectWrongNumberOfValues() {
        Schema schema = createStudentSchema();

        assertThrows(
                IllegalArgumentException.class,
                () -> new Tuple(
                        schema,
                        Arrays.asList(1, "Ahmed")
                )
        );
    }

    @Test
    void shouldRejectInvalidValueType() {
        Schema schema = createStudentSchema();

        assertThrows(
                IllegalArgumentException.class,
                () -> new Tuple(
                        schema,
                        Arrays.asList("wrong", "Ahmed", 4)
                )
        );
    }

    @Test
    void shouldAcceptNullValue() {
        Schema schema = createStudentSchema();

        Tuple tuple = new Tuple(
                schema,
                Arrays.asList(1, null, 4)
        );

        assertNull(tuple.getValue("name"));
    }

    @Test
    void shouldRejectNullSchema() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Tuple(
                        null,
                        Arrays.asList(1, "Ahmed", 4)
                )
        );
    }

    @Test
    void shouldRejectNullValuesList() {
        Schema schema = createStudentSchema();

        assertThrows(
                IllegalArgumentException.class,
                () -> new Tuple(schema, null)
        );
    }

    @Test
    void shouldKeepExactSchemaReference() {
        Schema schema = createStudentSchema();

        Tuple tuple = new Tuple(
                schema,
                Arrays.asList(1, "Ahmed", 4)
        );

        assertSame(schema, tuple.getSchema());
    }

    @Test
    void shouldCopyValuesList() {
        Schema schema = createStudentSchema();

        List<Object> values = Arrays.asList(1, "Ahmed", 4);

        Tuple tuple = new Tuple(schema, values);

        assertEquals(values, tuple.getValues());
    }

    @Test
    void shouldReturnUnmodifiableValues() {
        Schema schema = createStudentSchema();

        Tuple tuple = new Tuple(
                schema,
                Arrays.asList(1, "Ahmed", 4)
        );

        assertThrows(
                UnsupportedOperationException.class,
                () -> tuple.getValues().set(0, 99)
        );
    }

    @Test
    void equalTuplesShouldBeEqual() {
        Schema schema = createStudentSchema();

        Tuple first = new Tuple(
                schema,
                Arrays.asList(1, "Ahmed", 4)
        );

        Tuple second = new Tuple(
                schema,
                Arrays.asList(1, "Ahmed", 4)
        );

        assertEquals(first, second);
    }

    @Test
    void tuplesWithDifferentValuesShouldNotBeEqual() {
        Schema schema = createStudentSchema();

        Tuple first = new Tuple(
                schema,
                Arrays.asList(1, "Ahmed", 4)
        );

        Tuple second = new Tuple(
                schema,
                Arrays.asList(2, "Ahmed", 4)
        );

        assertNotEquals(first, second);
    }

    @Test
    void tuplesWithStructurallyEqualSchemasShouldBeEqual() {
        Schema firstSchema = createStudentSchema();
        Schema secondSchema = createStudentSchema();

        Tuple first = new Tuple(
                firstSchema,
                Arrays.asList(1, "Ahmed", 4)
        );

        Tuple second = new Tuple(
                secondSchema,
                Arrays.asList(1, "Ahmed", 4)
        );

        assertEquals(first, second);
    }

    @Test
    void equalTuplesShouldHaveSameHashCode() {
        Schema schema = createStudentSchema();

        Tuple first = new Tuple(
                schema,
                Arrays.asList(1, "Ahmed", 4)
        );

        Tuple second = new Tuple(
                schema,
                Arrays.asList(1, "Ahmed", 4)
        );

        assertEquals(first.hashCode(), second.hashCode());
    }

    @Test
    void shouldHaveReadableToString() {
        Schema schema = createStudentSchema();

        Tuple tuple = new Tuple(
                schema,
                Arrays.asList(1, "Ahmed", 4)
        );

        assertEquals("[1, Ahmed, 4]", tuple.toString());
    }
}