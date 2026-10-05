package com.jra.expression;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

import com.jra.model.Attribute;
import com.jra.model.Relation;
import com.jra.model.Schema;
import com.jra.predicate.Comparison;
import com.jra.predicate.Predicate;

class SelectionTest {

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
    void shouldCreateSelection() {
        Relation relation = createStudentRelation();

        RelationScan scan =
                new RelationScan(relation);

        Comparison predicate = new Comparison(
                relation.getSchema().getAttribute("department"),
                Comparison.Operator.EQUAL,
                "CS"
        );

        Selection selection = new Selection(
                scan,
                predicate
        );

        assertSame(scan, selection.getChild());
        assertSame(predicate, selection.getPredicate());
    }

    @Test
    void shouldReturnChildSchema() {
        Relation relation = createStudentRelation();

        RelationScan scan =
                new RelationScan(relation);

        Comparison predicate = new Comparison(
                relation.getSchema().getAttribute("department"),
                Comparison.Operator.EQUAL,
                "CS"
        );

        Selection selection = new Selection(
                scan,
                predicate
        );

        assertSame(
                relation.getSchema(),
                selection.getSchema()
        );
    }

    @Test
    void shouldRejectNullPredicate() {
        Relation relation = createStudentRelation();

        RelationScan scan =
                new RelationScan(relation);

        assertThrows(
                IllegalArgumentException.class,
                () -> new Selection(scan, null)
        );
    }

    @Test
    void shouldRejectNullChild() {
        Predicate predicate = new Comparison(
                new Attribute("year", Integer.class),
                Comparison.Operator.GREATER_THAN,
                2
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> new Selection(null, predicate)
        );
    }

    @Test
    void shouldRepresentSelectionTree() {
        Relation relation = createStudentRelation();

        RelationScan scan =
                new RelationScan(relation);

        Comparison predicate = new Comparison(
                relation.getSchema().getAttribute("department"),
                Comparison.Operator.EQUAL,
                "CS"
        );

        Selection selection =
                new Selection(scan, predicate);

        assertSame(
                relation,
                ((RelationScan) selection.getChild()).getRelation()
        );

        assertEquals(
                Comparison.Operator.EQUAL,
                ((Comparison) selection.getPredicate())
                        .getOperator()
        );
    }
}