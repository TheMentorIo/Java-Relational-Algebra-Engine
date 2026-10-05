package com.jra.expression;

import com.jra.model.Relation;
import com.jra.model.Schema;

public class RelationScan implements Expression {

    private final Relation relation;

    public RelationScan(Relation relation) {
        if (relation == null) {
            throw new IllegalArgumentException(
                    "Relation cannot be null"
            );
        }

        this.relation = relation;
    }

    public Relation getRelation() {
        return relation;
    }

    @Override
    public Schema getSchema() {
        return relation.getSchema();
    }
}