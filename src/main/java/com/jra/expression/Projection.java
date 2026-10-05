package com.jra.expression;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.jra.model.Attribute;
import com.jra.model.Schema;

public class Projection extends UnaryExpression {

    private final List<Attribute> attributes;
    private final Schema schema;

    public Projection(
            Expression child,
            List<Attribute> attributes
    ) {
        super(child);

        if (attributes == null) {
            throw new IllegalArgumentException(
                    "Projection attributes cannot be null"
            );
        }

        if (attributes.isEmpty()) {
            throw new IllegalArgumentException(
                    "Projection must contain at least one attribute"
            );
        }

        List<Attribute> projectedAttributes =
                new ArrayList<>();

        Schema childSchema = child.getSchema();

        for (Attribute attribute : attributes) {

            if (attribute == null) {
                throw new IllegalArgumentException(
                        "Projection attribute cannot be null"
                );
            }

            if (!childSchema.containsAttribute(
                    attribute.getName()
            )) {
                throw new IllegalArgumentException(
                        "Attribute does not exist in child schema: "
                        + attribute.getName()
                );
            }

            Attribute childAttribute =
                    childSchema.getAttribute(attribute.getName());

            if (!childAttribute.equals(attribute)) {
                throw new IllegalArgumentException(
                        "Projection attribute does not match "
                        + "the child schema: "
                        + attribute.getName()
                );
            }

            if (projectedAttributes.contains(attribute)) {
                throw new IllegalArgumentException(
                        "Duplicate projection attribute: "
                        + attribute.getName()
                );
            }

            projectedAttributes.add(attribute);
        }

        this.attributes =
                Collections.unmodifiableList(projectedAttributes);

        this.schema =
                new Schema(projectedAttributes);
    }

    public List<Attribute> getAttributes() {
        return attributes;
    }

    @Override
    public Schema getSchema() {
        return schema;
    }
}