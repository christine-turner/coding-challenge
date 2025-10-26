# Movie Database Frontend

## Summary
This repository contains the Angular frontend for our application.
It provides a modern, responsive user interface that connects to the backend API.
Allows user to:
- Search movies by title, year, and genres.
- Enter user credentials, supplied to the backend for Basic Authentication.

## Tech Stack
- Angular 20 – Frontend framework for building modular, reactive web applications
- TypeScript 5.9+ – Strongly typed language that enhances JavaScript development
- Angular Material & CDK – UI component library and utilities for responsive, accessible interfaces
- RxJS 7.8 – Reactive programming library for handling asynchronous data streams
- Yarn – Package manager for dependency management and scripts (v1.22.22 due to Angular dependency issues)
- Volta – Toolchain manager for consistent Node.js and Yarn versions across environments.

    Volta current active tools:
    - Node: v20.19.5 (default)
    - Yarn: v1.22.22 (default)
---
## Getting Started
### Option 1 - easiest: Run with Docker (no Node/Yarn needed)

#### 1. Install the following dependencies:

* Docker
* Git

#### 2. Clone the repository

```bash
git clone https://github.com/your-org/your-frontend-repo.git
cd your-frontend-repo
```

#### 3. Build and run the container

```bash
docker build -t angular-frontend .
docker run -p 4200:4200 angular-frontend
```

#### 4. Access the application
Open your browser and go to http://localhost:4200

### Option 2: Run Locally (requires Node/Yarn)

#### 1. Install the following dependencies:

* Node.js
 18+ (Recommended: use Volta
 to manage Node and Yarn versions for consistency.)
* Yarn
 1.22+ 
* Git
* IDE with TypeScript/Angular support (e.g. VS Code)

#### 2. Clone the repository

```bash
git clone https://github.com/your-org/your-frontend-repo.git
cd your-frontend-repo
```

#### 3. Install dependencies

```bash
yarn install
```

#### 4. Run the development server

```bash
yarn start
```

#### 5. Open the application
Visit http://localhost:4200
 in your browser.
The app will automatically reload when you make changes to the source files.

## Project Structure
```
├── src/
│   └── app/
│       ├── components/      # Reusable UI components
│       ├── service/         # API and data services
│       └── app.module.ts    # Root module
├── angular.json
├── package.json
└── README.md
```