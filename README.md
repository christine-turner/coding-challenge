# Project Overview

This repository contains the full application stack for the project, including:
- Frontend: Angular 20 (Yarn, TypeScript)
- Backend: Quarkus (Java 17+, Gradle)
- Containerization: Docker & Docker Compose

You can run the entire stack locally using Docker — no need to install Node.js, Yarn, Java, or Gradle on your machine.

# Getting Started
## Prerequisites

### Installation
Make sure the following are installed:

- [Docker](https://www.docker.com/get-started) - 24+  
- [Docker Compose](https://docs.docker.com/compose/install/) (bundled with Docker Desktop)  
- [Git](https://git-scm.com/downloads)

### Run with Docker Compose

#### 1. Clone the repository
````
git clone https://github.com/christine-turner/coding-challenge.git
cd coding-challenge
````
#### 2. Ensure the Docker daemon is running
Make sure Docker Desktop (or your Docker service) is started and the daemon is active.

#### 3. Build and start all services
Navigate to the project root level using your favourate terminal.
````
docker compose up --build
````
This may take several minutes and while the services are starting, the links listed below won't work. The command builds both the backend and frontend Docker images, starts the containers and exposes each service on the configured ports.

#### 4. Access the application

* Frontend → http://localhost:4200
* Backend API → http://localhost:8080
* Backend DevUI → http://localhost:8080/q/dev-ui/extensions (you can view the source code here, review available end points, check all tests pass etc)

# Project Structure
````
├── backend/                 # Quarkus backend service (Java + Gradle)
│   └── movie-api/
│       ├── src/
│       ├── build.gradle
│       ├── Dockerfile       # Dockerfile for backend
│       └── README.md
│
├── frontend/                # Angular frontend (Yarn + TypeScript)
│   └── movie-ui/
│       ├── src/
│       ├── package.json
│       ├── Dockerfile       # Dockerfile for frontend
│       └── README.md
│
├── docker-compose.yml       # Root-level Compose file for both services
└── README.md                # This file
````

## Local Development (Optional)

If you prefer to run services separately during development, you can find information in the relevant READMEs in [/frontend/movie-ui/README](/frontend/movie-ui/README.md) and [/backend/movie-api/README](/backend/movie-api/README.md).
