package com.jra.model;

import java.util.Objects;

public class Attribute {

    private final String name;
    private final Class<?> type;

    public Attribute(String name, Class<?> type) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Attribute name cannot be null or blank");
        }

        if (type == null) {
            throw new IllegalArgumentException("Attribute type cannot be null");
        }

        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public Class<?> getType() {
        return type;
    }

    public boolean isValidValue(Object value) {
        if (value == null) {
            return true;
        }

        return wrapPrimitive(type).isInstance(value);
    }

    private Class<?> wrapPrimitive(Class<?> type) {
        if (!type.isPrimitive()) {
            return type;
        }

        if (type == int.class) {
            return Integer.class;
        }
        if (type == long.class) {
            return Long.class;
        }
        if (type == double.class) {
            return Double.class;
        }
        if (type == float.class) {
            return Float.class;
        }
        if (type == boolean.class) {
            return Boolean.class;
        }
        if (type == char.class) {
            return Character.class;
        }
        if (type == byte.class) {
            return Byte.class;
        }
        if (type == short.class) {
            return Short.class;
        }

        return type;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Attribute other)) {
            return false;
        }

        return name.equals(other.name)
                && type.equals(other.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, type);
    }

    @Override
    public String toString() {
        return name + " : " + type.getSimpleName();
    }
}