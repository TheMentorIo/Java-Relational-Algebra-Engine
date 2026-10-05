package com.jra.model;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class RelationTest {

    private Schema createStudentSchema() {
        return new Schema(
                Arrays.asList(
                        new Attribute("student_id", Integer.class),
                        new Attribute("name", String.class),
                        new Attribute("year", Integer.class)
                )
        );
    }

    private Tuple createStudentTuple(Schema schema) {
        return new Tuple(
                schema,
                Arrays.asList(1, "Ahmed", 4)
        );
    }

    @Test
    void shouldCreateRelation() {
        Schema schema = createStudentSchema();

        Relation relation = new Relation(
                "Student",
                schema
        );

        assertEquals("Student", relation.getName());
        assertSame(schema, relation.getSchema());
        assertEquals(0, relation.size());
    }

    @Test
    void shouldRejectNullRelationName() {
        Schema schema = createStudentSchema();

        assertThrows(
                IllegalArgumentException.class,
                () -> new Relation(null, schema)
        );
    }

    @Test
    void shouldRejectBlankRelationName() {
        Schema schema = createStudentSchema();

        assertThrows(
                IllegalArgumentException.class,
                () -> new Relation("   ", schema)
        );
    }

    @Test
    void shouldRejectNullSchema() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Relation("Student", null)
        );
    }

    @Test
    void shouldAddTuple() {
        Schema schema = createStudentSchema();

        Relation relation = new Relation(
                "Student",
                schema
        );

        Tuple tuple = createStudentTuple(schema);

        relation.addTuple(tuple);

        assertEquals(1, relation.size());
        assertTrue(relation.containsTuple(tuple));
    }

    @Test
    void shouldRejectNullTuple() {
        Schema schema = createStudentSchema();

        Relation relation = new Relation(
                "Student",
                schema
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> relation.addTuple(null)
        );
    }

    @Test
    void shouldRejectTupleWithDifferentSchemaReference() {
        Schema relationSchema = createStudentSchema();
        Schema tupleSchema = createStudentSchema();

        Relation relation = new Relation(
                "Student",
                relationSchema
        );

        Tuple tuple = createStudentTuple(tupleSchema);

        assertThrows(
                IllegalArgumentException.class,
                () -> relation.addTuple(tuple)
        );
    }

    @Test
    void shouldRemoveTuple() {
        Schema schema = createStudentSchema();

        Relation relation = new Relation(
                "Student",
                schema
        );

        Tuple tuple = createStudentTuple(schema);

        relation.addTuple(tuple);
        relation.removeTuple(tuple);

        assertEquals(0, relation.size());
        assertFalse(relation.containsTuple(tuple));
    }

    @Test
    void shouldIgnoreDuplicateTuple() {
        Schema schema = createStudentSchema();

        Relation relation = new Relation(
                "Student",
                schema
        );

        Tuple first = new Tuple(
                schema,
                Arrays.asList(1, "Ahmed", 4)
        );

        Tuple second = new Tuple(
                schema,
                Arrays.asList(1, "Ahmed", 4)
        );

        relation.addTuple(first);
        relation.addTuple(second);

        assertEquals(1, relation.size());
    }

    @Test
    void shouldContainEqualTuple() {
        Schema schema = createStudentSchema();

        Relation relation = new Relation(
                "Student",
                schema
        );

        Tuple stored = new Tuple(
                schema,
                Arrays.asList(1, "Ahmed", 4)
        );

        Tuple equalTuple = new Tuple(
                schema,
                Arrays.asList(1, "Ahmed", 4)
        );

        relation.addTuple(stored);

        assertTrue(relation.containsTuple(equalTuple));
    }

    @Test
    void shouldRemoveEqualTuple() {
        Schema schema = createStudentSchema();

        Relation relation = new Relation(
                "Student",
                schema
        );

        Tuple stored = new Tuple(
                schema,
                Arrays.asList(1, "Ahmed", 4)
        );

        Tuple equalTuple = new Tuple(
                schema,
                Arrays.asList(1, "Ahmed", 4)
        );

        relation.addTuple(stored);
        relation.removeTuple(equalTuple);

        assertEquals(0, relation.size());
    }

    @Test
    void shouldReturnUnmodifiableTuples() {
        Schema schema = createStudentSchema();

        Relation relation = new Relation(
                "Student",
                schema
        );

        Tuple tuple = createStudentTuple(schema);
        relation.addTuple(tuple);

        assertThrows(
                UnsupportedOperationException.class,
                () -> relation.getTuples().clear()
        );
    }

    @Test
    void tupleShouldReferenceRelationSchema() {
        Schema schema = createStudentSchema();

        Relation relation = new Relation(
                "Student",
                schema
        );

        Tuple tuple = createStudentTuple(schema);

        relation.addTuple(tuple);

        assertSame(
                relation.getSchema(),
                tuple.getSchema()
        );
    }
}