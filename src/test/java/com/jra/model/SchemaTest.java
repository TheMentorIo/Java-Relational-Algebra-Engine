package com.jra.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

class SchemaTest {

    @Test
    void shouldCreateEmptySchema() {
        Schema schema = new Schema();

        assertEquals(0, schema.size());
    }

    @Test
    void shouldAddAttribute() {
        Schema schema = new Schema();

        Attribute id = new Attribute("id", Integer.class);
        schema.addAttribute(id);

        assertEquals(1, schema.size());
        assertEquals(id, schema.getAttribute(0));
    }

    @Test
    void shouldFindAttributeByName() {
        Schema schema = new Schema();

        schema.addAttribute(new Attribute("id", Integer.class));
        schema.addAttribute(new Attribute("name", String.class));

        Attribute attribute = schema.getAttribute("name");

        assertEquals("name", attribute.getName());
        assertEquals(String.class, attribute.getType());
    }

    @Test
    void shouldReturnCorrectAttributeIndex() {
        Schema schema = new Schema();

        schema.addAttribute(new Attribute("id", Integer.class));
        schema.addAttribute(new Attribute("name", String.class));

        assertEquals(0, schema.indexOf("id"));
        assertEquals(1, schema.indexOf("name"));
        assertEquals(-1, schema.indexOf("age"));
    }

    @Test
    void shouldRejectDuplicateAttributeName() {
        Schema schema = new Schema();

        schema.addAttribute(new Attribute("id", Integer.class));

        assertThrows(
                IllegalArgumentException.class,
                () -> schema.addAttribute(new Attribute("id", String.class))
        );
    }

    @Test
    void shouldRejectNullAttribute() {
        Schema schema = new Schema();

        assertThrows(
                IllegalArgumentException.class,
                () -> schema.addAttribute(null)
        );
    }

    @Test
    void shouldRemoveAttribute() {
        Schema schema = new Schema();

        schema.addAttribute(new Attribute("id", Integer.class));
        schema.addAttribute(new Attribute("name", String.class));

        schema.removeAttribute("id");

        assertEquals(1, schema.size());
        assertEquals("name", schema.getAttribute(0).getName());
    }

    @Test
    void shouldRejectRemovingUnknownAttribute() {
        Schema schema = new Schema();

        assertThrows(
                IllegalArgumentException.class,
                () -> schema.removeAttribute("id")
        );
    }
}