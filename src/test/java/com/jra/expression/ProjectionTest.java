package com.jra.expression;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

import com.jra.model.Attribute;
import com.jra.model.Relation;
import com.jra.model.Schema;

class ProjectionTest {

    private Schema createStudentSchema() {
        return new Schema(
                List.of(
                        new Attribute("student_id", Integer.class),
                        new Attribute("name", String.class),
                        new Attribute("department", String.class),
                        new Attribute("year", Integer.class)
                )
        );
    }

    private Relation createStudentRelation() {
        return new Relation(
                "Student",
                createStudentSchema()
        );
    }

    @Test
    void shouldCreateProjection() {
        Relation relation = createStudentRelation();

        RelationScan scan =
                new RelationScan(relation);

        Attribute name =
                relation.getSchema().getAttribute("name");

        Attribute department =
                relation.getSchema().getAttribute("department");

        Projection projection = new Projection(
                scan,
                List.of(name, department)
        );

        assertSame(scan, projection.getChild());

        assertEquals(
                List.of(name, department),
                projection.getAttributes()
        );
    }

    @Test
    void shouldCreateProjectedSchema() {
        Relation relation = createStudentRelation();

        RelationScan scan =
                new RelationScan(relation);

        Attribute name =
                relation.getSchema().getAttribute("name");

        Attribute department =
                relation.getSchema().getAttribute("department");

        Projection projection = new Projection(
                scan,
                List.of(name, department)
        );

        Schema schema = projection.getSchema();

        assertEquals(2, schema.size());
        assertEquals(name, schema.getAttribute(0));
        assertEquals(
                department,
                schema.getAttribute(1)
        );
    }

    @Test
    void shouldPreserveProjectionAttributeOrder() {
        Relation relation = createStudentRelation();

        RelationScan scan =
                new RelationScan(relation);

        Attribute year =
                relation.getSchema().getAttribute("year");

        Attribute name =
                relation.getSchema().getAttribute("name");

        Projection projection = new Projection(
                scan,
                List.of(year, name)
        );

        assertEquals(
                "year",
                projection.getSchema()
                        .getAttribute(0)
                        .getName()
        );

        assertEquals(
                "name",
                projection.getSchema()
                        .getAttribute(1)
                        .getName()
        );
    }

    @Test
    void shouldRejectNullAttributesList() {
        Relation relation = createStudentRelation();

        RelationScan scan =
                new RelationScan(relation);

        assertThrows(
                IllegalArgumentException.class,
                () -> new Projection(scan, null)
        );
    }

    @Test
    void shouldRejectEmptyAttributesList() {
        Relation relation = createStudentRelation();

        RelationScan scan =
                new RelationScan(relation);

        assertThrows(
                IllegalArgumentException.class,
                () -> new Projection(scan, List.of())
        );
    }

    @Test
    void shouldRejectNullAttribute() {
        Relation relation = createStudentRelation();

        RelationScan scan =
                new RelationScan(relation);

        assertThrows(
                IllegalArgumentException.class,
                () -> new Projection(
                        scan,
                        java.util.Arrays.asList(
                                relation.getSchema()
                                        .getAttribute("name"),
                                null
                        )
                )
        );
    }

    @Test
    void shouldRejectUnknownAttribute() {
        Relation relation = createStudentRelation();

        RelationScan scan =
                new RelationScan(relation);

        Attribute unknown =
                new Attribute("unknown", String.class);

        assertThrows(
                IllegalArgumentException.class,
                () -> new Projection(
                        scan,
                        List.of(unknown)
                )
        );
    }

    @Test
    void shouldRejectAttributeWithWrongType() {
        Relation relation = createStudentRelation();

        RelationScan scan =
                new RelationScan(relation);

        Attribute wrongNameAttribute =
                new Attribute(
                        "name",
                        Integer.class
                );

        assertThrows(
                IllegalArgumentException.class,
                () -> new Projection(
                        scan,
                        List.of(wrongNameAttribute)
                )
        );
    }

    @Test
    void shouldRejectDuplicateAttributes() {
        Relation relation = createStudentRelation();

        RelationScan scan =
                new RelationScan(relation);

        Attribute name =
                relation.getSchema().getAttribute("name");

        assertThrows(
                IllegalArgumentException.class,
                () -> new Projection(
                        scan,
                        List.of(name, name)
                )
        );
    }

    @Test
    void shouldRejectNullChild() {
        Attribute name =
                new Attribute("name", String.class);

        assertThrows(
                IllegalArgumentException.class,
                () -> new Projection(
                        null,
                        List.of(name)
                )
        );
    }

    @Test
    void projectedSchemaShouldBeDifferentObject() {
        Relation relation = createStudentRelation();

        RelationScan scan =
                new RelationScan(relation);

        Attribute name =
                relation.getSchema().getAttribute("name");

        Projection projection = new Projection(
                scan,
                List.of(name)
        );

        assertNotSame(
                relation.getSchema(),
                projection.getSchema()
        );
    }
}