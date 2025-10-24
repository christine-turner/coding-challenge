package com.example.persistence.json;

import com.example.model.Genre;
import com.example.model.Movie;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;

import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class JsonMovieLoaderTest {

    @Inject
    JsonMovieLoader loader;

    @Test
    void shouldLoadMoviesFromConfiguredFile() {
        Set<Movie> movies = loader.getMovies();

        assertNotNull(movies, "Movies should not be null");
        assertFalse(movies.isEmpty(), "Movies set should not be empty");
    }

    @Test
    void shouldContainExpectedKnownMovies() {
        Set<Movie> movies = loader.getMovies();

        Movie action = movies.stream()
                .filter(m -> "Sample Action Movie".equals(m.getTitle()))
                .findFirst()
                .orElse(null);

        Movie romance = movies.stream()
                .filter(m -> "Romantic Drama".equals(m.getTitle()))
                .findFirst()
                .orElse(null);

        assertNotNull(action, "Expected Sample Action Movie to be present");
        assertEquals(2020, action.getYear());
        assertTrue(action.getGenres().contains(Genre.ACTION), "Expected Action genre");

        assertNotNull(romance, "Expected Romantic Drama to be present");
        assertEquals(2018, romance.getYear());
        assertTrue(romance.getGenres().contains(Genre.ROMANCE));
        assertTrue(romance.getGenres().contains(Genre.DRAMA));
    }

    @Test
    void shouldLoadAllMandatoryFields() {
        Set<Movie> movies = loader.getMovies();

        for (Movie m : movies) {
            assertNotNull(m.getTitle(), "Title should not be null");
            assertNotNull(m.getYear(), "Year should not be null");
            assertNotNull(m.getGenres(), "Genres should not be null");
            assertFalse(m.getGenres().isEmpty(), "Genres should not be empty");
        }
    }

    @Test
    void shouldIgnoreDuplicateMoviesIfPresent() {
        // This test verifies the loader's use of Set (no duplicates)
        Set<Movie> movies = loader.getMovies();

        long distinctCount = movies.stream()
                .map(Movie::getTitle)
                .distinct()
                .count();

        assertEquals(movies.size(), distinctCount,
                "Expected all movie titles to be unique");
    }

    @Test
    void shouldNotReloadMoviesMultipleTimes() {
        // Checks caching/lazy load behavior
        Set<Movie> firstCall = loader.getMovies();
        Set<Movie> secondCall = loader.getMovies();

        assertSame(firstCall, secondCall,
                "Expected same reference — should not reload file repeatedly");
    }
}
