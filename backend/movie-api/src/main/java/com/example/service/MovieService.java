package com.example.service;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.example.persistence.MovieRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import com.example.api.MovieSearchCriteria;
import com.example.model.Genre;
import com.example.model.Movie;

@ApplicationScoped
public class MovieService {
  @Inject
  MovieRepository movieRepository;

  /**
   * Returns all movies matching the given search criteria.
   */
  public Set<Movie> findMovies(MovieSearchCriteria criteria) {
    return movieRepository.findAll().stream()
        .filter(movieMatches(criteria))
        .collect(Collectors.toUnmodifiableSet());
  }

  /**
   * Builds a predicate that checks if a movie matches the given search criteria.
   */
  private Predicate<Movie> movieMatches(MovieSearchCriteria criteria) {
    return movie -> matchesTitle(movie, criteria.getTitle())
        && matchesReleaseYear(movie, criteria.getYear())
        && matchesGenres(movie, criteria.getGenres());
  }

  private boolean matchesTitle(Movie movie, String title) {
    if (title == null || title.isBlank()) {
      return true;
    }
    return Optional.ofNullable(movie.getTitle())
        .map(t -> t.toLowerCase().contains(title.toLowerCase()))
        .orElse(false);
  }

  private boolean matchesReleaseYear(Movie movie, Integer year) {
    return year == null || Objects.equals(movie.getYear(), year);
  }

  private boolean matchesGenres(Movie movie, Set<Genre> genres) {
    if (genres == null || genres.isEmpty()) {
      return true;
    }
    return Optional.ofNullable(movie.getGenres())
        .map(movieGenres -> movieGenres.containsAll(genres))
        .orElse(false);
  }
}
