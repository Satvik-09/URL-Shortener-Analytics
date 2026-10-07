 URL Shortener with JWT Authentication

A Spring Boot backend that shortens URLs using Base62 encoding, with full JWT-based authentication, role-based route protection, and Docker Compose deployment backed by a persisted MySQL database.

Features
URL Shortening — converts long URLs into compact, unique short codes using Base62 encoding of the database-assigned ID
Authentication — user registration and login with BCrypt-hashed passwords
Authorization — JWT-based stateless authentication; protected routes require a valid Bearer token, with role-based access control for admin-only endpoints
Validation & Error Handling — request validation via Bean Validation annotations, with a global exception handler returning consistent JSON error responses
Containerized — fully Dockerized with Docker Compose, running the application alongside a persisted MySQL instance
Tech Stack
Java 21, Spring Boot 4.1.1
Spring Security, JWT (jjwt)
Spring Data JPA, Hibernate
MySQL (Docker), H2 (local dev)
Docker & Docker Compose
JUnit 5, Mockito
Maven
Architecture
Client Request
      │
      ▼
JwtAuthFilter ── checks every request for a Bearer token
      │
      ▼
SecurityConfig ── decides: public route, or does this need auth?
      │
      ├── Public (permitAll) ──────────────┐
      │                                     ▼
      └── Protected (authenticated/role) ──▶ Controller
                                               │
                                               ▼
                                            Service
                                               │
                                               ▼
                                           Repository
                                               │
                                               ▼
                                            Database

(GlobalExceptionHandler wraps the Controller→Service→Repository chain,
 catching exceptions and returning consistent JSON error responses.)
API Endpoints
Method	Endpoint	Auth required	Description
POST	/auth/register	No	Register a new user
POST	/auth/login	No	Authenticate and receive a JWT
POST	/shorten	No	Shorten a long URL
GET	/{code}	No	Redirect to the original URL
GET	/protected-test	Yes (Bearer token)	Example protected route
Getting Started
Prerequisites
Java 21
Maven (or use the included mvnw wrapper)
Docker Desktop
Run locally (without Docker)
bash
./mvnw spring-boot:run

The app starts on http://localhost:8080, backed by an in-memory H2 database.

Run with Docker Compose (recommended)
bash
docker-compose up --build

This starts the application alongside a MySQL container, with data persisted in a Docker volume.

To rebuild after code changes:

bash
docker-compose up --build

To stop:

bash
docker-compose down

To stop and wipe the database volume:

bash
docker-compose down -v
Example Usage

Register:

bash
curl -X POST http://localhost:8080/auth/register \
  -H "Content-Type: application/json" \
  -d '{"username": "satvik", "password": "pass123"}'

Login:

bash
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username": "satvik", "password": "pass123"}'

Returns a JWT in the response body.

Shorten a URL:

bash
curl -X POST http://localhost:8080/shorten \
  -H "Content-Type: application/json" \
  -d '{"url": "https://www.example.com"}'

Access a protected route:

bash
curl -H "Authorization: Bearer <your-token>" http://localhost:8080/protected-test
Running Tests
bash
./mvnw test

Unit tests cover the Base62 encoder/decoder, the URL shortening service (with a mocked repository), and JWT token generation/validation, including rejection of tampered tokens.

Development Workflow

This project was built incrementally, with each layer tested before moving to the next:

Entity, repository, and Base62 encoding logic — tested standalone
Service and controller layers — manually tested via curl/Postman
DTOs, validation, and global exception handling
JWT authentication: registration, login, token-based route protection, and role-based access control
Containerization with Docker and Docker Compose, migrating from in-memory H2 to persisted MySQL
Unit testing with JUnit and Mockito
Future Improvements
Redis caching for frequently accessed short URL lookups
Refresh token support
Rate limiting on the /shorten endpoint
CI/CD pipeline for automated testing and deployment
Author

Satvik — GitHub
