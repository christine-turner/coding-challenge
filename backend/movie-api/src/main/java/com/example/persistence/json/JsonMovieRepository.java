package com.example.persistence.json;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import com.example.model.Movie;
import com.example.persistence.MovieRepository;

import java.util.Set;

/**
 * JSON file repository that loads movies from a static data source.
 * 
 * This implementation can be replaced later by a database-backed
 * repository (e.g., using Panache) without changing any
 * higher-level layers (service or REST resource).
 */
@ApplicationScoped
public class JsonMovieRepository implements MovieRepository {

    @Inject
    JsonMovieLoader jsonLoader;

    @Override
    public Set<Movie> findAll() {
        return jsonLoader.getMovies();
    }
}