package com.jra.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Schema {

    private final List<Attribute> attributes;

    public Schema() {
        this.attributes = new ArrayList<>();
    }

    public Schema(List<Attribute> attributes) {
        if (attributes == null) {
            throw new IllegalArgumentException(
                    "Attributes cannot be null"
            );
        }

        this.attributes = new ArrayList<>();

        for (Attribute attribute : attributes) {
            addAttribute(attribute);
        }
    }

    public void addAttribute(Attribute attribute) {
        if (attribute == null) {
            throw new IllegalArgumentException(
                    "Attribute cannot be null"
            );
        }

        if (containsAttribute(attribute.getName())) {
            throw new IllegalArgumentException(
                    "Duplicate attribute name: "
                    + attribute.getName()
            );
        }

        attributes.add(attribute);
    }

    public void removeAttribute(String attributeName) {
        int index = indexOf(attributeName);

        if (index == -1) {
            throw new IllegalArgumentException(
                    "Attribute not found: " + attributeName
            );
        }

        attributes.remove(index);
    }

    public Attribute getAttribute(int index) {
        return attributes.get(index);
    }

    public Attribute getAttribute(String attributeName) {
        int index = indexOf(attributeName);

        if (index == -1) {
            throw new IllegalArgumentException(
                    "Attribute not found: " + attributeName
            );
        }

        return attributes.get(index);
    }

    public int indexOf(String attributeName) {
        for (int i = 0; i < attributes.size(); i++) {
            if (attributes.get(i).getName().equals(attributeName)) {
                return i;
            }
        }

        return -1;
    }

    public boolean containsAttribute(String attributeName) {
        return indexOf(attributeName) != -1;
    }

    public int size() {
        return attributes.size();
    }

    public List<Attribute> getAttributes() {
        return Collections.unmodifiableList(attributes);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Schema other)) {
            return false;
        }

        return attributes.equals(other.attributes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(attributes);
    }

    @Override
    public String toString() {
        return attributes.toString();
    }
}