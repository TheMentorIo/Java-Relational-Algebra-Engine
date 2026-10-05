package com.jra.expression;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

import com.jra.model.Attribute;
import com.jra.model.Relation;
import com.jra.model.Schema;

class RelationScanTest {

    @Test
    void shouldCreateRelationScan() {
        Schema schema = new Schema(
                List.of(
                        new Attribute("id", Integer.class),
                        new Attribute("name", String.class)
                )
        );

        Relation relation = new Relation("Student", schema);

        RelationScan scan = new RelationScan(relation);

        assertSame(relation, scan.getRelation());
    }

    @Test
    void shouldReturnRelationSchema() {
        Schema schema = new Schema(
                List.of(
                        new Attribute("id", Integer.class),
                        new Attribute("name", String.class)
                )
        );

        Relation relation = new Relation("Student", schema);

        RelationScan scan = new RelationScan(relation);

        assertSame(schema, scan.getSchema());
    }

    @Test
    void shouldRejectNullRelation() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new RelationScan(null)
        );
    }
}