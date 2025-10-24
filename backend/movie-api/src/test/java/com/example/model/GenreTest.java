package com.example.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GenreTest {

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    void testSerializeGenre() throws Exception {
        String json = mapper.writeValueAsString(Genre.ACTION);
        assertEquals("\"Action\"", json, "Genre should serialize to its display name");
    }

    @Test
    void testDeserializeGenre() throws Exception {
        Genre genre = mapper.readValue("\"Action\"", Genre.class);
        assertEquals(Genre.ACTION, genre, "JSON string should deserialize to ACTION enum");

        // Case-insensitive check
        Genre genre2 = mapper.readValue("\"action\"", Genre.class);
        assertEquals(Genre.ACTION, genre2);
    }

    @Test
    void testUnknownGenreThrows() {
        assertThrows(IllegalArgumentException.class,
                () -> Genre.fromString("UnknownGenre"));
    }
}
