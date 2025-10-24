package com.example.api;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.BeanParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

import java.util.Set;

import org.jboss.resteasy.reactive.RestResponse;

import com.example.model.Movie;
import com.example.service.MovieService;

@ApplicationScoped
@Path("/api/movies")
public class MovieResource {

    @Inject MovieService service;

    /**
     * Search movies using optional query parameters.
     * 
     * @param criteria title (partial, case-insensitive), year, and/or genres 
     * Assumption made that if multiple genres are provided, only movies matching all genre are returned 
     * @return 200 OK with filtered movies, 400 Bad Request if invalid genres
     */
    @GET
    public RestResponse<Set<Movie>> findMovies(@BeanParam MovieSearchCriteria criteria) {
        try {
            Set<Movie> movies = service.findMovies(criteria);
            return RestResponse.ok(movies);
        } catch (IllegalArgumentException e) {
            return RestResponse.status(RestResponse.Status.BAD_REQUEST, Set.of());
        }
    }

}
