package com.example.persistence;

import java.util.Set;

import com.example.model.Movie;

public interface MovieRepository {

    /**
     * Returns all movies from the underlying data source.
     * This could be a JSON file, database, or remote API.
     */
    Set<Movie> findAll();
}
