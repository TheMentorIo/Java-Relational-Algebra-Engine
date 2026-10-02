package com.jra.model;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

class TupleTest {

    private Schema createStudentSchema() {
        Schema schema = new Schema();

        schema.addAttribute(new Attribute("id", Integer.class));
        schema.addAttribute(new Attribute("name", String.class));
        schema.addAttribute(new Attribute("year", Integer.class));

        return schema;
    }

    @Test
    void shouldCreateTuple() {
        Schema schema = createStudentSchema();

        Tuple tuple = new Tuple(
                schema,
                List.of(1, "Ahmed", 4)
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
                List.of(1, "Ahmed", 4)
        );

        assertEquals(1, tuple.getValue("id"));
        assertEquals("Ahmed", tuple.getValue("name"));
        assertEquals(4, tuple.getValue("year"));
    }

    @Test
    void shouldRejectWrongNumberOfValues() {
        Schema schema = createStudentSchema();

        assertThrows(
                IllegalArgumentException.class,
                () -> new Tuple(schema, List.of(1, "Ahmed"))
        );
    }

    @Test
    void shouldRejectInvalidValueType() {
        Schema schema = createStudentSchema();

        assertThrows(
                IllegalArgumentException.class,
                () -> new Tuple(schema, List.of("1", "Ahmed", 4))
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
    void shouldKeepReferenceToSameSchema() {
        Schema schema = createStudentSchema();

        Tuple tuple = new Tuple(
                schema,
                List.of(1, "Ahmed", 4)
        );

        assertSame(schema, tuple.getSchema());
    }

    @Test
    void shouldRejectNullSchema() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Tuple(null, List.of(1))
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
}