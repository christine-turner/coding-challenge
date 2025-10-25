package com.example.api;

import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.BeanParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

import java.util.Set;

import org.jboss.resteasy.reactive.RestResponse;

import com.example.model.Movie;
import com.example.service.MovieService;

import io.quarkus.logging.Log;

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
    @RolesAllowed({"admin", "user"})
    public RestResponse<Set<Movie>> findMovies(@BeanParam MovieSearchCriteria criteria) {
        try {
            Set<Movie> movies = service.findMovies(criteria);
            Log.infof("Returning %d movies", movies.size());
            return RestResponse.ok(movies);
        } catch (IllegalArgumentException e) {
            return RestResponse.status(RestResponse.Status.BAD_REQUEST, Set.of());
        }
    }

    // Example endpoints to demonstrate role-based access control
    @GET
    @Path("/public")
    public Response publicEndpoint() {
        return Response.ok("Public movies endpoint (no auth required)").build();
    }

    @GET
    @Path("/user")
    @RolesAllowed("user")
    public Response userEndpoint() {
        return Response.ok("User movies endpoint (user role)").build();
    }

    @GET
    @Path("/admin")
    @RolesAllowed("admin")
    public Response adminEndpoint() {
        return Response.ok("Admin movies endpoint (admin role)").build();
    }
}
