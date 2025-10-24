package com.example.persistence.json;

import com.example.model.Movie;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.logging.Log;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.io.InputStream;
import java.util.Collections;
import java.util.Set;

@ApplicationScoped
public class JsonMovieLoader {

    @ConfigProperty(name = "movies.file", defaultValue = "movies.json")
    String moviesFile;

    private Set<Movie> movies;

    @PostConstruct
    void init() {
        movies = loadMovies();
    }

    public Set<Movie> getMovies() {
        return movies;
    }

    private Set<Movie> loadMovies() {
        try (InputStream movieFile = getClass().getClassLoader().getResourceAsStream(moviesFile)) {
            if (movieFile == null) {
                throw new RuntimeException("Could not find " + moviesFile);
            }

            ObjectMapper mapper = new ObjectMapper();
            Set<Movie> loaded = mapper.readValue(movieFile, new TypeReference<>() {});
            Log.infof("Loaded %d movies from %s", loaded.size(), moviesFile);
            return Collections.unmodifiableSet(loaded);
        } catch (Exception e) {
            Log.error("Failed to load movies file: " + moviesFile, e);
            throw new RuntimeException("Failed to load movies file: " + moviesFile, e);
        }
    }
}