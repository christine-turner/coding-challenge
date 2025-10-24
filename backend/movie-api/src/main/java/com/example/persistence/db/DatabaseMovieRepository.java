package com.example.persistence.db;

import com.example.model.Movie;
import com.example.persistence.MovieRepository;

import java.util.Set;

public class DatabaseMovieRepository implements MovieRepository {

    @Override
    public Set<Movie> findAll() {
        throw new UnsupportedOperationException("Not implemented");
        // can fairly easily implement this using Panache
        // e.g. return Movie.listAll().stream().collect(Collectors.toUnmodifiableSet());
    }
}
