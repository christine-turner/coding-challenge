# Movie Backend Service
## Summary
A Quarkus-based REST backend for managing and searching movies, with role-based Basic Authentication.
- Search movies by title, year, and genres.
- Basic Authentication with Elytron properties file.
  - Role-based access control: `admin` and `user`.
- Proper HTTP response codes:
  - `200 OK` for successful queries
  - `400 Bad Request` for invalid genres
  - `401 Unauthorized` and `403 Forbidden` for restricted access
- Unit and integration tests for REST endpoints and service logic.

## Tech Stack
- Java 17+
- Quarkus
- Gradle
- RESTEasy
- Elytron Security for Basic Auth
- JUnit 5 & Mockito for tests
- RestAssured for integration tests
---

# Getting Started
## Option 1: Run Locally (requires Java/Gradle)
### 1. Install the follow dependencies:
- [Java 17 or later](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)  
- [Gradle 8+](https://gradle.org/releases/)  
- [Git](https://git-scm.com/downloads)  
- [VS Code](https://code.visualstudio.com/) (this is optional, with Java extensions, or other IDE of choice) 

### 2. Clone the repository

````
git clone https://github.com/christine-turner/coding-challenge.git
cd coding-challenge
````
### 3. Running the Application

#### Development Mode

    ./gradlew quarkusDev

- Access the API at `http://localhost:8080/api/movies`

#### Production Build

    ./gradlew build
    java -jar build/quarkus-app/quarkus-run.jar

#### Testing

Run all tests:

    ./gradlew test

- Unit tests cover `MovieService` and `MovieResource`.
- Integration tests cover endpoint security (`@TestSecurity`) and behavior with mock services.

## Option 2: Run with Docker (no Java/Gradle needed)
Refer to root level [README](../../README.md) for instructions on how to run Docker.

---

# Interacting with the Application
## Introduction

All endpoints are secured with **HTTP Basic Authentication**. These can be tested easily using cURL - other alternatives are Postman or Bruno.

Example cURL request:
```bash
curl -u user:user123 http://localhost:8080/api/movies
```
Roles:
- `admin` role: access to all endpoints
- `user` role: limited access to allowed endpoints

## Available Endpoint : GET `/api/movies`

Search movies with optional query parameters:

- `title` (partial, case-insensitive)
- `year`
- `genres` (comma-separated; only movies matching all genres are returned)

**Response Codes:**

- 200 OK — movies found or empty set
- 400 Bad Request — invalid genres
- 401 Unauthorized — missing credentials
- 403 Forbidden — insufficient roles

## Example cURL Requests

### 1. Unauthorized (no credentials supplied to an authorised endpoint)
```bash
curl -i http://localhost:8080/api/movies
```

#### Expected Response
````
HTTP/1.1 401 Unauthorized
WWW-Authenticate: basic
Content-Length: 0
````
---

### 2. Public access (no authentication required)
```bash
curl http://localhost:8080/api/movies/public
```

#### Expected Response (200 OK)
```
Public movies endpoint (no auth required)
```
---
### 3. User role access

```bash
curl -u user:user123 http://localhost:8080/api/movies/user
```
#### Expected Response (200 OK)

### 4. User movies endpoint (user role)

Unauthorized Access Example:
```bash
curl http://localhost:8080/api/movies/user
```

#### Expected Response (401 Unauthorized)
````
{"error": "Unauthorized", "message": "Full authentication is required to access this resource"}
````
---

### 5. Admin role access
```bash
curl -u admin:admin123 http://localhost:8080/api/movies/admin
```
#### Expected Response (200 OK)

### 6. Admin movies endpoint (admin role)

### 7. Unauthorized Access Example (User Trying Admin Endpoint):
```bash
curl -u user:user123 http://localhost:8080/api/movies/admin
```
#### Expected Response (403 Forbidden)
````
{"error": "Forbidden", "message": "Access is denied"}
````
## Notes

- Plain-text passwords in `users.properties` are **for demo purposes only**. In production, use hashed passwords and secure storage/alternative authentication protocol.
- `@RolesAllowed` annotations enforce role-based access per endpoint.
- For testing security in `@QuarkusTest`, `@TestSecurity` annotations can simulate user roles reliably without asserting exact `WWW-Authenticate` headers.

