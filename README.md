 # URL Shortener with JWT Authentication
 [![CI](https://github.com/Satvik-09/URL-Shortener-Analytics/actions/workflows/ci.yml/badge.svg)](https://github.com/Satvik-09/URL-Shortener-Analytics/actions/workflows/ci.yml)

A Spring Boot backend that shortens URLs using Base62 encoding. It has JWT-based authentication, role-based route protection, unit tests, a GitHub Actions CI pipeline, and Docker Compose deployment backed by a persisted MySQL database.

## Features

- **URL shortening**: converts long URLs into compact, unique short codes by Base62-encoding the database-assigned ID
- **Authentication**: registration and login with BCrypt-hashed passwords
- **Authorization**: stateless JWT authentication; protected routes require a valid Bearer token, and admin routes require the `ADMIN` role
- **Validation and error handling**: Bean Validation on request DTOs, plus a global exception handler that returns consistent JSON errors
- **Containerized**: multi-stage Docker build, with Docker Compose running the app next to a MySQL container with a persistent volume
- **Tested and automated**: JUnit 5 and Mockito unit tests run automatically on every push and pull request via GitHub Actions

## Tech Stack

- Java 21, Spring Boot 4.1.1
- Spring Security, JWT (jjwt)
- Spring Data JPA, Hibernate
- MySQL (Docker), H2 (local development)
- Docker and Docker Compose
- JUnit 5, Mockito
- GitHub Actions
- Maven

## Architecture

```
Client Request
      |
      v
JwtFilter -- reads the Authorization header, validates the Bearer token
      |
      v
SecurityConfig -- is this route public, or does it need authentication / a role?
      |
      v
Controller --> Service --> Repository --> Database

GlobalExceptionHandler wraps the Controller -> Service -> Repository chain
and converts exceptions into consistent JSON error responses.
```

## API Endpoints

| Method | Endpoint | Auth required | Description |
|---|---|---|---|
| POST | `/auth/register` | No | Register a new user |
| POST | `/auth/login` | No | Authenticate and receive a JWT |
| POST | `/shorten` | No | Shorten a long URL |
| GET | `/{code}` | No | Redirect to the original URL |
| GET | `/protected-test` | Yes (Bearer token) | Example protected route |
| ANY | `/admin/**` | Yes (`ADMIN` role) | Admin-only routes |

## Getting Started

### Prerequisites
- Java 21
- Docker Desktop
- Maven (or use the included `mvnw` wrapper)

### Run with Docker Compose (recommended)
```bash
git clone https://github.com/Satvik-09/URL-Shortener-Analytics.git
cd URL-Shortener-Analytics
docker-compose up --build
```
This starts the application and a MySQL container. Database data is stored in a named Docker volume, so it survives restarts.

```bash
docker-compose down       # stop containers, keep data
docker-compose down -v    # stop containers and delete the database volume
```
After changing code, re-run `docker-compose up --build`. Images are snapshots, so a rebuild is required for changes to appear.

### Run locally without Docker
```bash
./mvnw spring-boot:run
```
Uses an in-memory H2 database, so data is lost on every restart.

## Example Usage

**Register**
```bash
curl -X POST http://localhost:8080/auth/register \
  -H "Content-Type: application/json" \
  -d '{"username": "satvik", "password": "pass123"}'
```

**Login** (returns a JWT)
```bash
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username": "satvik", "password": "pass123"}'
```

**Shorten a URL**
```bash
curl -X POST http://localhost:8080/shorten \
  -H "Content-Type: application/json" \
  -d '{"url": "https://www.example.com"}'
```

**Call a protected route**
```bash
curl -H "Authorization: Bearer <your-token>" http://localhost:8080/protected-test
```
A missing or tampered token returns `403`.

## Running Tests

```bash
./mvnw test
```

Test coverage:
- `Base62Test`: encode/decode round trip
- `UrlServiceTest`: short code generation and the not-found case, using a mocked repository (Mockito)
- `JwtUtilTest`: token generation and validation, plus rejection of tampered tokens

## Continuous Integration

A GitHub Actions workflow (`.github/workflows/ci.yml`) runs on every push to `main` and on every pull request targeting `main`. It checks out the code, sets up JDK 21, and runs `./mvnw test`. The badge at the top of this README shows the current status.

## Development Workflow

- Work is tracked as GitHub Issues
- Each change is developed on a feature branch (for example `feature/junit-tests`)
- Changes are merged through pull requests that reference the issue (`Closes #N`)
- CI must pass before merging

## Future Improvements

- Redis caching for frequently accessed short URL lookups
- Move the JWT secret and database credentials out of source and into environment variables or a secrets manager
- Refresh token support
- Rate limiting on `/shorten`
- Deployment to a cloud host

## Author

Satvik: [GitHub](https://github.com/Satvik-09)
