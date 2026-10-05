package com.jra.model;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

public class Relation {

    private final String name;
    private final Schema schema;
    private final Set<Tuple> tuples;

    public Relation(String name, Schema schema) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException(
                    "Relation name cannot be null or blank"
            );
        }

        if (schema == null) {
            throw new IllegalArgumentException(
                    "Schema cannot be null"
            );
        }

        this.name = name;
        this.schema = schema;
        this.tuples = new LinkedHashSet<>();
    }

    public String getName() {
        return name;
    }

    public Schema getSchema() {
        return schema;
    }

    public void addTuple(Tuple tuple) {
        if (tuple == null) {
            throw new IllegalArgumentException(
                    "Tuple cannot be null"
            );
        }

        if (tuple.getSchema() != schema) {
            throw new IllegalArgumentException(
                    "Tuple schema must be the same schema as the relation"
            );
        }

        tuples.add(tuple);
    }

    public void removeTuple(Tuple tuple) {
        tuples.remove(tuple);
    }

    public boolean containsTuple(Tuple tuple) {
        return tuples.contains(tuple);
    }

    public Set<Tuple> getTuples() {
        return Collections.unmodifiableSet(tuples);
    }

    public int size() {
        return tuples.size();
    }

    @Override
    public String toString() {
        return name + " " + tuples;
    }
}