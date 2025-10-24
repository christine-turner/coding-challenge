package com.example.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.example.model.Genre;

import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.QueryParam;

public class MovieSearchCriteria {
    @QueryParam("title")
    @DefaultValue("") // empty string instead of null
    private String title;

    @QueryParam("year")
    private Integer year;

    @QueryParam("genre")
    private List<String> genreStrings;

    public String getTitle() {
        return title;
    }

    public Integer getYear() {
        return year;
    }

    /**
     * Converts the genre query parameter list to a set of Genre enums.
     *
     * @throws IllegalArgumentException if any genre is unknown
     */
    public Set<Genre> getGenres() {
        if (genreStrings == null)
            return Set.of();
        return genreStrings.stream()
                .map(Genre::fromString)
                .collect(Collectors.toSet());
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setGenreStrings(List<String> genreStrings) {
        this.genreStrings = new ArrayList<String>();
        this.genreStrings.addAll(genreStrings);
    }
}
