package com.jra.model;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class RelationTest {

    private Schema createStudentSchema() {
        Schema schema = new Schema();

        schema.addAttribute(new Attribute("id", Integer.class));
        schema.addAttribute(new Attribute("name", String.class));

        return schema;
    }

    @Test
    void shouldCreateRelation() {
        Schema schema = createStudentSchema();

        Relation relation = new Relation("Student", schema);

        assertEquals("Student", relation.getName());
        assertSame(schema, relation.getSchema());
        assertEquals(0, relation.size());
    }

    @Test
    void shouldAddTuple() {
        Schema schema = createStudentSchema();

        Relation relation = new Relation("Student", schema);

        Tuple tuple = new Tuple(
                schema,
                List.of(1, "Ahmed")
        );

        relation.addTuple(tuple);

        assertEquals(1, relation.size());
        assertTrue(relation.containsTuple(tuple));
    }

    @Test
    void shouldRejectTupleWithDifferentSchema() {
        Schema schema1 = createStudentSchema();
        Schema schema2 = createStudentSchema();

        Relation relation = new Relation("Student", schema1);

        Tuple tuple = new Tuple(
                schema2,
                List.of(1, "Ahmed")
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> relation.addTuple(tuple)
        );
    }

    @Test
    void shouldRejectNullTuple() {
        Schema schema = createStudentSchema();

        Relation relation = new Relation("Student", schema);

        assertThrows(
                IllegalArgumentException.class,
                () -> relation.addTuple(null)
        );
    }

    @Test
    void shouldRemoveTuple() {
        Schema schema = createStudentSchema();

        Relation relation = new Relation("Student", schema);

        Tuple tuple = new Tuple(
                schema,
                List.of(1, "Ahmed")
        );

        relation.addTuple(tuple);
        relation.removeTuple(tuple);

        assertEquals(0, relation.size());
        assertFalse(relation.containsTuple(tuple));
    }

    @Test
    void shouldKeepTupleSchemaIdenticalToRelationSchema() {
        Schema schema = createStudentSchema();

        Relation relation = new Relation("Student", schema);

        Tuple tuple = new Tuple(
                schema,
                List.of(1, "Ahmed")
        );

        relation.addTuple(tuple);

        assertSame(
                relation.getSchema(),
                tuple.getSchema()
        );
    }
}