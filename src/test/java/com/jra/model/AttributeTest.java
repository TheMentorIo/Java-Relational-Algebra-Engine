package com.jra.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class AttributeTest {

    @Test
    void shouldCreateAttribute() {
        Attribute attribute = new Attribute("id", Integer.class);

        assertEquals("id", attribute.getName());
        assertEquals(Integer.class, attribute.getType());
    }

    @Test
    void shouldAcceptValidValue() {
        Attribute attribute = new Attribute("id", Integer.class);

        assertTrue(attribute.isValidValue(10));
    }

    @Test
    void shouldRejectInvalidValue() {
        Attribute attribute = new Attribute("id", Integer.class);

        assertFalse(attribute.isValidValue("10"));
    }

    @Test
    void shouldAcceptNullValue() {
        Attribute attribute = new Attribute("id", Integer.class);

        assertTrue(attribute.isValidValue(null));
    }

    @Test
    void shouldRejectBlankName() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Attribute("", Integer.class)
        );
    }

    @Test
    void shouldRejectNullType() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Attribute("id", null)
        );
    }
}