package com.jra.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class AttributeTest {

    @Test
    void shouldCreateAttribute() {
        Attribute attribute = new Attribute("student_id", Integer.class);

        assertEquals("student_id", attribute.getName());
        assertEquals(Integer.class, attribute.getType());
    }

    @Test
    void shouldAcceptValidValue() {
        Attribute attribute = new Attribute("student_id", Integer.class);

        assertTrue(attribute.isValidValue(101));
    }

    @Test
    void shouldRejectInvalidValue() {
        Attribute attribute = new Attribute("student_id", Integer.class);

        assertFalse(attribute.isValidValue("101"));
    }

    @Test
    void shouldAcceptNullValue() {
        Attribute attribute = new Attribute("student_id", Integer.class);

        assertTrue(attribute.isValidValue(null));
    }

    @Test
    void shouldAcceptPrimitiveCompatibleValue() {
        Attribute attribute = new Attribute("student_id", int.class);

        assertTrue(attribute.isValidValue(101));
    }

    @Test
    void shouldRejectIncompatiblePrimitiveValue() {
        Attribute attribute = new Attribute("student_id", int.class);

        assertFalse(attribute.isValidValue("101"));
    }

    @Test
    void shouldRejectNullName() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Attribute(null, Integer.class)
        );
    }

    @Test
    void shouldRejectBlankName() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Attribute("   ", Integer.class)
        );
    }

    @Test
    void shouldRejectNullType() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Attribute("student_id", null)
        );
    }

    @Test
    void equalAttributesShouldBeEqual() {
        Attribute first = new Attribute("student_id", Integer.class);
        Attribute second = new Attribute("student_id", Integer.class);

        assertEquals(first, second);
    }

    @Test
    void differentAttributeNamesShouldNotBeEqual() {
        Attribute first = new Attribute("student_id", Integer.class);
        Attribute second = new Attribute("course_id", Integer.class);

        assertNotEquals(first, second);
    }

    @Test
    void differentAttributeTypesShouldNotBeEqual() {
        Attribute first = new Attribute("student_id", Integer.class);
        Attribute second = new Attribute("student_id", String.class);

        assertNotEquals(first, second);
    }

    @Test
    void equalAttributesShouldHaveSameHashCode() {
        Attribute first = new Attribute("student_id", Integer.class);
        Attribute second = new Attribute("student_id", Integer.class);

        assertEquals(first.hashCode(), second.hashCode());
    }

    @Test
    void shouldHaveReadableToString() {
        Attribute attribute = new Attribute("student_id", Integer.class);

        assertEquals("student_id : Integer", attribute.toString());
    }
}