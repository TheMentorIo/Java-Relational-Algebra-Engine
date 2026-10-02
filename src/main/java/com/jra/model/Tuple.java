package com.jra.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Tuple {

    private final Schema schema;
    private final List<Object> values;

    public Tuple(Schema schema, List<Object> values) {
        if (schema == null) {
            throw new IllegalArgumentException("Schema cannot be null");
        }

        if (values == null) {
            throw new IllegalArgumentException("Values cannot be null");
        }

        if (values.size() != schema.size()) {
            throw new IllegalArgumentException(
                    "Number of values must match schema size"
            );
        }

        for (int i = 0; i < values.size(); i++) {
            Attribute attribute = schema.getAttribute(i);
            Object value = values.get(i);

            if (!attribute.isValidValue(value)) {
                throw new IllegalArgumentException(
                        "Invalid value for attribute '" + attribute.getName() +
                        "': " + value
                );
            }
        }

        this.schema = schema;
        this.values = new ArrayList<>(values);
    }

    public Schema getSchema() {
        return schema;
    }

    public Object getValue(int index) {
        return values.get(index);
    }

    public Object getValue(String attributeName) {
        int index = schema.indexOf(attributeName);

        if (index == -1) {
            throw new IllegalArgumentException(
                    "Attribute not found: " + attributeName
            );
        }

        return values.get(index);
    }

    public List<Object> getValues() {
        return Collections.unmodifiableList(values);
    }

    public int size() {
        return values.size();
    }

    @Override
    public String toString() {
        return values.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Tuple other)) {
            return false;
        }

        return schema.equals(other.schema)
                && values.equals(other.values);
    }

    @Override
    public int hashCode() {
        return 31 * schema.hashCode() + values.hashCode();
    }
}