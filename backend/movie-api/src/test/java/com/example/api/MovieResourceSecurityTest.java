package com.example.api;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.*;

@QuarkusTest
class MovieResourceSecurityTest {

    private static final String BASE = "/api/movies";

    @Test
    void testAccessWithoutCredentialsShouldReturn401() {
        RestAssured
            .given()
            .when().get(BASE)
            .then()
            .statusCode(401);
    }

    @Test
    void testAccessWithUserCredentialsShouldSucceed() {
        RestAssured
            .given()
            .auth().basic("user", "user123")
            .when().get(BASE)
            .then()
            .statusCode(200)
            .body(anything()); // response structure verified elsewhere
    }

    @Test
    void testAccessWithAdminCredentialsShouldSucceed() {
        RestAssured
            .given()
            .auth().basic("admin", "admin123")
            .when().get(BASE)
            .then()
            .statusCode(200)
            .body(anything());
    }
}
