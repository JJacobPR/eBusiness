# eBusiness Transport App Spring Boot API

A backend RESTful API built with Spring Boot, PostgreSQL, and Docker.

---

## 🚀 Setup Instructions

### 1. Prerequisites
- Java 21
- Maven
- Docker & Docker Compose

---

### 2. Build the Project

```bash
mvn clean package
```

### 3. Run with Docker
```bash
docker-compose up --build
```
This will:

- Build and run the Spring Boot app on port 8080

- Start PostgreSQL database on port 5433

- Launch pgAdmin (optional) on port 5050

### 4. 🌐 API Access
- Base URL: http://localhost:8080/api/v1
- Swagger UI: http://localhost:8080/api/v1/swagger.html

### 5. 📂 Project Structure
```bash
src/main/java/com/ebusiness
├── entity         # JPA entities
├── repository     # Spring Data JPA Repositories
├── service        # Business logic
├── rest           # REST API Controllers
├── dto            # DTOs
├── security       # Spring Security Config

```

### 6. 🧼 Clean Up
```bash
docker-compose down --volumes
```