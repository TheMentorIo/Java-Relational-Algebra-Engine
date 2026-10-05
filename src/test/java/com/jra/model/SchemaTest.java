package com.jra.model;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class SchemaTest {

    @Test
    void shouldCreateEmptySchema() {
        Schema schema = new Schema();

        assertEquals(0, schema.size());
        assertTrue(schema.getAttributes().isEmpty());
    }

    @Test
    void shouldCreateSchemaFromAttributes() {
        Attribute studentId = new Attribute("student_id", Integer.class);
        Attribute name = new Attribute("name", String.class);

        Schema schema = new Schema(
                Arrays.asList(studentId, name)
        );

        assertEquals(2, schema.size());
        assertEquals(studentId, schema.getAttribute(0));
        assertEquals(name, schema.getAttribute(1));
    }

    @Test
    void shouldAddAttribute() {
        Schema schema = new Schema();

        Attribute studentId = new Attribute("student_id", Integer.class);

        schema.addAttribute(studentId);

        assertEquals(1, schema.size());
        assertEquals(studentId, schema.getAttribute(0));
    }

    @Test
    void shouldFindAttributeByName() {
        Schema schema = new Schema(
                Arrays.asList(
                        new Attribute("student_id", Integer.class),
                        new Attribute("name", String.class)
                )
        );

        Attribute attribute = schema.getAttribute("name");

        assertEquals("name", attribute.getName());
        assertEquals(String.class, attribute.getType());
    }

    @Test
    void shouldReturnCorrectAttributeIndex() {
        Schema schema = new Schema(
                Arrays.asList(
                        new Attribute("student_id", Integer.class),
                        new Attribute("name", String.class),
                        new Attribute("year", Integer.class)
                )
        );

        assertEquals(0, schema.indexOf("student_id"));
        assertEquals(1, schema.indexOf("name"));
        assertEquals(2, schema.indexOf("year"));
    }

    @Test
    void shouldReturnMinusOneForUnknownAttribute() {
        Schema schema = new Schema(
                List.of(
                        new Attribute("student_id", Integer.class)
                )
        );

        assertEquals(-1, schema.indexOf("name"));
    }

    @Test
    void shouldCheckWhetherAttributeExists() {
        Schema schema = new Schema(
                List.of(
                        new Attribute("student_id", Integer.class)
                )
        );

        assertTrue(schema.containsAttribute("student_id"));
        assertFalse(schema.containsAttribute("name"));
    }

    @Test
    void shouldRejectDuplicateAttributeName() {
        Schema schema = new Schema();

        schema.addAttribute(
                new Attribute("student_id", Integer.class)
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> schema.addAttribute(
                        new Attribute("student_id", String.class)
                )
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
    void shouldRejectNullAttributeList() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Schema(null)
        );
    }

    @Test
    void shouldRemoveAttribute() {
        Schema schema = new Schema(
                Arrays.asList(
                        new Attribute("student_id", Integer.class),
                        new Attribute("name", String.class)
                )
        );

        schema.removeAttribute("student_id");

        assertEquals(1, schema.size());
        assertFalse(schema.containsAttribute("student_id"));
        assertTrue(schema.containsAttribute("name"));
    }

    @Test
    void shouldRejectRemovingUnknownAttribute() {
        Schema schema = new Schema(
                List.of(
                        new Attribute("student_id", Integer.class)
                )
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> schema.removeAttribute("name")
        );
    }

    @Test
    void shouldReturnUnmodifiableAttributes() {
        Schema schema = new Schema(
                List.of(
                        new Attribute("student_id", Integer.class)
                )
        );

        List<Attribute> attributes = schema.getAttributes();

        assertThrows(
                UnsupportedOperationException.class,
                () -> attributes.add(
                        new Attribute("name", String.class)
                )
        );
    }

    @Test
    void equalSchemasShouldBeEqual() {
        Schema first = new Schema(
                Arrays.asList(
                        new Attribute("student_id", Integer.class),
                        new Attribute("name", String.class)
                )
        );

        Schema second = new Schema(
                Arrays.asList(
                        new Attribute("student_id", Integer.class),
                        new Attribute("name", String.class)
                )
        );

        assertEquals(first, second);
    }

    @Test
    void differentSchemasShouldNotBeEqual() {
        Schema first = new Schema(
                List.of(
                        new Attribute("student_id", Integer.class)
                )
        );

        Schema second = new Schema(
                List.of(
                        new Attribute("course_id", Integer.class)
                )
        );

        assertNotEquals(first, second);
    }

    @Test
    void attributeOrderShouldMatter() {
        Schema first = new Schema(
                Arrays.asList(
                        new Attribute("student_id", Integer.class),
                        new Attribute("name", String.class)
                )
        );

        Schema second = new Schema(
                Arrays.asList(
                        new Attribute("name", String.class),
                        new Attribute("student_id", Integer.class)
                )
        );

        assertNotEquals(first, second);
    }

    @Test
    void equalSchemasShouldHaveSameHashCode() {
        Schema first = new Schema(
                List.of(
                        new Attribute("student_id", Integer.class)
                )
        );

        Schema second = new Schema(
                List.of(
                        new Attribute("student_id", Integer.class)
                )
        );

        assertEquals(first.hashCode(), second.hashCode());
    }
}