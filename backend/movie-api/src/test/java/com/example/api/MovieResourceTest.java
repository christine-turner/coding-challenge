package com.example.api;

import com.example.model.Genre;
import com.example.model.Movie;
import com.example.service.MovieService;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * Unit-level integration test for {@link MovieResource}.
 *
 * <p>This test class verifies that the MovieResource REST endpoint correctly:
 * <ul>
 *   <li>Handles successful search results</li>
 *   <li>Returns empty results appropriately</li>
 *   <li>Handles invalid genre input with a 400 response</li>
 * </ul>
 *
 * <p>The underlying {@link MovieService} is mocked using Mockito to isolate
 * and test only the REST layer behavior, not the business logic.
 */
@QuarkusTest
class MovieResourceTest {

    private MovieResource movieResource;
    private MovieService movieServiceMock;

    /**
     * Sets up a new instance of {@link MovieResource} before each test
     * and injects a mock {@link MovieService}.
     *
     */
    @BeforeEach
    void setUp() {
        movieServiceMock = Mockito.mock(MovieService.class);
        movieResource = new MovieResource();
        movieResource.service = movieServiceMock; // Manual injection of mock
    }

    /**
     * Test Case: Valid movie search returns a matching result.
     *
     * <p>Scenario:
     * <ul>
     *   <li>The mocked service returns a single "Inception" movie.</li>
     *   <li>Request title is "inception" (lowercase).</li>
     *   <li>The endpoint should return HTTP 200 with one matching movie.</li>
     * </ul>
     */
    @Test
    void testFindMoviesReturnsMatchingMovies() {
        // Arrange
        Movie movie = new Movie("Inception", 2010, Set.of(Genre.ACTION, Genre.SCIENCE_FICTION));
        when(movieServiceMock.findMovies(any(MovieSearchCriteria.class)))
                .thenReturn(Set.of(movie));

        MovieSearchCriteria criteria = new MovieSearchCriteria();
        criteria.setTitle("inception");

        // Act
        var response = movieResource.findMovies(criteria);

        // Assert
        assertEquals(200, response.getStatus(), "Expected 200 OK for valid query");
        assertEquals(1, response.getEntity().size(), "Expected one matching movie");
        assertTrue(response.getEntity().contains(movie), "Returned movie should match mock");
    }

    /**
     * Test Case: Search with no results returns an empty set and HTTP 200.
     *
     * <p>Scenario:
     * <ul>
     *   <li>No movies match the given title.</li>
     *   <li>Service returns an empty set.</li>
     *   <li>Endpoint should return HTTP 200 with an empty JSON array.</li>
     * </ul>
     */
    @Test
    void testFindMoviesWithEmptyResult() {
        // Arrange
        when(movieServiceMock.findMovies(any(MovieSearchCriteria.class)))
                .thenReturn(Set.of());

        MovieSearchCriteria criteria = new MovieSearchCriteria();
        criteria.setTitle("Nonexistent");

        // Act
        var response = movieResource.findMovies(criteria);

        // Assert
        assertEquals(200, response.getStatus(), "Expected 200 OK for empty results");
        assertTrue(response.getEntity().isEmpty(), "Entity should be an empty set");
    }

    /**
     * Test Case: Invalid genre input returns HTTP 400.
     *
     * <p>Scenario:
     * <ul>
     *   <li>The service throws an IllegalArgumentException for an unknown genre.</li>
     *   <li>The resource should catch this and respond with 400 Bad Request.</li>
     *   <li>The returned entity is an empty set to maintain response structure consistency.</li>
     * </ul>
     */
    @Test
    void testFindMoviesWithInvalidGenre() {
        // Arrange
        when(movieServiceMock.findMovies(any(MovieSearchCriteria.class)))
                .thenThrow(new IllegalArgumentException("Unknown genre"));

        MovieSearchCriteria criteria = new MovieSearchCriteria();
        criteria.setGenreStrings(Set.of("InvalidGenre").stream().toList());

        // Act
        var response = movieResource.findMovies(criteria);

        // Assert
        assertEquals(400, response.getStatus(), "Expected 400 Bad Request for invalid genre");
        assertEquals(Set.of(), response.getEntity(), "Entity should be an empty set on error");
    }
}
