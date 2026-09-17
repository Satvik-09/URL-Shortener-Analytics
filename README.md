# URL Shortener with Analytics

A backend service built with Java and Spring Boot that shortens long URLs into compact codes and redirects users to the original URL.

## Features (Current)
- Shorten a long URL into a short code
- Redirect from a short code to its original URL
- Data persisted using Spring Data JPA with an H2 in-memory database

## Features (Planned)
- Custom Base62 encoding for short code generation (replacing temporary random codes)
- Click analytics per short URL (count, last accessed)
- Input validation and centralized exception handling
- Optional link expiry
- Deployment to a live environment

## Tech Stack
- Java 21
- Spring Boot
- Spring Data JPA
- H2 Database (in-memory, for development)
- Maven

## API Endpoints

### Shorten a URL
POST /shorten
Content-Type: application/json

Request body:
{
  "url": "https://www.google.com"
}

Response: 200 OK with the generated short code as plain text.

### Redirect to Original URL
GET /{code}

Response: 302 Found, redirects to the original URL associated with that code.

## Running Locally
1. Clone the repository
2. Open the project in your IDE (IntelliJ recommended)
3. Run UrlShortnerApplication.java
4. App starts on http://localhost:8080
5. Access the H2 console at http://localhost:8080/h2-console
   - JDBC URL: jdbc:h2:mem:urlshortenerdb
   - Username: sa
   - Password: (leave blank)

## Testing
Use Postman (or similar) to test endpoints — see API Endpoints section above for request/response formats.

## Project Structure
src/main/java/com/satvik/url_shortner/
- entity/       -> JPA entities (database table mappings)
- repository/   -> Spring Data JPA repositories
- service/      -> Business logic
- controller/   -> REST API endpoints
- dto/          -> Request/response data transfer objects (in progress)
- exception/    -> Custom exception handling (in progress)

## Author
Satvik
