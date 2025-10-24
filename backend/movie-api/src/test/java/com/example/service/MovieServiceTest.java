package com.example.service;

import com.example.api.MovieSearchCriteria;
import com.example.model.Genre;
import com.example.model.Movie;
import com.example.persistence.MovieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

/**
 * Unit tests for {@link MovieService}.
 *
 * <p>This class verifies the filtering logic of the MovieService using a mocked
 * {@link MovieRepository}. It ensures that search criteria for title, genre, and year
 * are correctly applied when retrieving movies.
 *
 * <p>These are <b>unit tests</b>, so the persistence layer is not actually accessed.
 */
class MovieServiceTest {

    private MovieService movieService;
    private MovieRepository movieRepository;

    /**
     * Sets up a fresh instance of MovieService before each test,
     * injecting a mocked MovieRepository to isolate business logic.
     */
    @BeforeEach
    void setup() {
        movieRepository = Mockito.mock(MovieRepository.class);
        movieService = new MovieService(); // Use real service implementation
        movieService.movieRepository = movieRepository; // Manual dependency injection
    }

    /**
     * Verifies that movies are filtered by title, performing a case-insensitive
     * and partial match.
     *
     * <p>Scenario:
     * <ul>
     *   <li>Two movies with "Matrix" in their titles exist.</li>
     *   <li>Criteria title = "matrix" (lowercase).</li>
     *   <li>Both movies should match, ignoring case.</li>
     * </ul>
     */
    @Test
    void testFindMoviesByTitle() {
        Movie movie1 = new Movie("The Matrix", 1999, Set.of(Genre.ACTION, Genre.SCIENCE_FICTION));
        Movie movie2 = new Movie("Matrix Reloaded", 2003, Set.of(Genre.ACTION));

        when(movieRepository.findAll()).thenReturn(Set.of(movie1, movie2));

        MovieSearchCriteria criteria = new MovieSearchCriteria();
        criteria.setTitle("matrix");

        Set<Movie> results = movieService.findMovies(criteria);

        assertEquals(2, results.size(), "Should find both 'Matrix' movies");
        assertTrue(results.contains(movie1));
        assertTrue(results.contains(movie2));
    }

    /**
     * Verifies that movies are correctly filtered by genre.
     *
     * <p>Scenario:
     * <ul>
     *   <li>Two movies exist with different genres.</li>
     *   <li>Criteria genre = "Action".</li>
     *   <li>Only movies containing ACTION in their genres should match.</li>
     * </ul>
     */
    @Test
    void testFindMoviesByGenre() {
        Movie movie1 = new Movie("Movie A", 2020, Set.of(Genre.ACTION, Genre.DRAMA));
        Movie movie2 = new Movie("Movie B", 2021, Set.of(Genre.COMEDY));

        when(movieRepository.findAll()).thenReturn(Set.of(movie1, movie2));

        MovieSearchCriteria criteria = new MovieSearchCriteria();
        criteria.setGenreStrings(java.util.List.of("Action"));

        Set<Movie> results = movieService.findMovies(criteria);

        assertEquals(1, results.size(), "Only Action movies should match");
        assertTrue(results.contains(movie1));
    }
}
