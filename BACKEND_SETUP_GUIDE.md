# Spring Boot Backend Setup Guide for Freelancer Platform

## Overview
This guide will help you set up a Spring Boot backend for the Freelancer platform from scratch. Follow these steps to build a solid foundation for your backend application.

## Prerequisites
✅ Java 17 is installed and configured
✅ Maven 3.9.11 is installed and configured
✅ Documentation files are available in the `Documents` folder

## Recommended Technology Stack

### Core Framework
- **Spring Boot 3.2.x** (Latest stable version with Java 17 support)
- **Spring Web** - For RESTful APIs
- **Spring Data JPA** - For database operations
- **Spring Security** - For authentication and authorization

### Database
- **PostgreSQL** or **MySQL** - For production
- **H2 Database** - For development and testing (in-memory)

### Additional Dependencies
- **Lombok** - Reduce boilerplate code
- **Spring Validation** - For input validation
- **SpringDoc OpenAPI** - For API documentation (Swagger UI)
- **MapStruct** - For DTO mapping
- **Spring Boot DevTools** - For hot reload during development

## Project Structure Recommendation

```
freelancer-backend/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── freelancer/
│   │   │           ├── FreelancerApplication.java
│   │   │           ├── config/
│   │   │           │   ├── SecurityConfig.java
│   │   │           │   ├── SwaggerConfig.java
│   │   │           │   └── CorsConfig.java
│   │   │           ├── controller/
│   │   │           │   ├── AuthController.java
│   │   │           │   ├── UserController.java
│   │   │           │   ├── ProjectController.java
│   │   │           │   └── FreelancerController.java
│   │   │           ├── service/
│   │   │           │   ├── AuthService.java
│   │   │           │   ├── UserService.java
│   │   │           │   ├── ProjectService.java
│   │   │           │   └── FreelancerService.java
│   │   │           ├── repository/
│   │   │           │   ├── UserRepository.java
│   │   │           │   ├── ProjectRepository.java
│   │   │           │   └── FreelancerRepository.java
│   │   │           ├── model/
│   │   │           │   ├── User.java
│   │   │           │   ├── Project.java
│   │   │           │   ├── Freelancer.java
│   │   │           │   └── enums/
│   │   │           ├── dto/
│   │   │           │   ├── request/
│   │   │           │   │   ├── LoginRequest.java
│   │   │           │   │   ├── RegisterRequest.java
│   │   │           │   │   └── ProjectRequest.java
│   │   │           │   └── response/
│   │   │           │       ├── UserResponse.java
│   │   │           │       ├── ProjectResponse.java
│   │   │           │       └── ApiResponse.java
│   │   │           ├── exception/
│   │   │           │   ├── GlobalExceptionHandler.java
│   │   │           │   ├── ResourceNotFoundException.java
│   │   │           │   └── BadRequestException.java
│   │   │           └── util/
│   │   │               ├── JwtUtil.java
│   │   │               └── ValidationUtil.java
│   │   └── resources/
│   │       ├── application.yml
│   │       ├── application-dev.yml
│   │       ├── application-prod.yml
│   │       └── db/
│   │           └── migration/
│   └── test/
│       └── java/
│           └── com/
│               └── freelancer/
│                   ├── controller/
│                   ├── service/
│                   └── repository/
├── pom.xml
├── .gitignore
└── README.md
```

## Step-by-Step Setup Instructions

### Option 1: Using Spring Initializr (Recommended for Beginners)

1. **Visit Spring Initializr**
   - Go to https://start.spring.io/

2. **Configure Project Metadata**
   - Project: Maven
   - Language: Java
   - Spring Boot: 3.2.x (latest stable)
   - Group: com.freelancer
   - Artifact: freelancer-backend
   - Name: freelancer-backend
   - Package name: com.freelancer
   - Packaging: Jar
   - Java: 17

3. **Add Dependencies**
   - Spring Web
   - Spring Data JPA
   - Spring Security
   - Spring Validation
   - Lombok
   - Spring Boot DevTools
   - PostgreSQL Driver (or MySQL Driver)
   - H2 Database
   - SpringDoc OpenAPI UI

4. **Generate and Download**
   - Click "Generate" button
   - Extract the downloaded zip file
   - Copy contents to your repository

### Option 2: Using Maven Command Line

Run this command in your repository root:

```bash
mvn archetype:generate \
  -DgroupId=com.freelancer \
  -DartifactId=freelancer-backend \
  -DarchetypeArtifactId=maven-archetype-quickstart \
  -DarchetypeVersion=1.4 \
  -DinteractiveMode=false
```

Then manually add Spring Boot parent and dependencies to `pom.xml`.

### Option 3: Manual Setup

Create the basic project structure manually and configure `pom.xml` with Spring Boot dependencies.

## Initial Configuration Files

### application.yml (Basic Configuration)

```yaml
spring:
  application:
    name: freelancer-backend
  
  # Database Configuration
  datasource:
    url: jdbc:h2:mem:testdb
    driver-class-name: org.h2.Driver
    username: sa
    password: 
  
  # JPA Configuration
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
    properties:
      hibernate:
        format_sql: true
  
  # H2 Console (for development)
  h2:
    console:
      enabled: true
      path: /h2-console

server:
  port: 8080
  
# API Documentation
springdoc:
  api-docs:
    path: /api-docs
  swagger-ui:
    path: /swagger-ui.html
```

### .gitignore

```
# Compiled class files
*.class
target/
*.jar
*.war
*.ear

# Log files
*.log

# IDE files
.idea/
*.iml
.vscode/
.project
.classpath
.settings/

# OS files
.DS_Store
Thumbs.db

# Application properties with sensitive data
application-local.yml
application-local.properties
```

## Basic Package Setup

### 1. Main Application Class

```java
package com.freelancer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FreelancerApplication {
    public static void main(String[] args) {
        SpringApplication.run(FreelancerApplication.class, args);
    }
}
```

### 2. Health Check Controller

```java
package com.freelancer.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HealthController {
    
    @GetMapping("/health")
    public String health() {
        return "Backend is running!";
    }
}
```

## Development Workflow

### 1. Build the Project
```bash
mvn clean install
```

### 2. Run the Application
```bash
mvn spring-boot:run
```

### 3. Test the Application
```bash
# Health check
curl http://localhost:8080/api/health

# Swagger UI
Open browser: http://localhost:8080/swagger-ui.html

# H2 Console (if using H2)
Open browser: http://localhost:8080/h2-console
```

### 4. Run Tests
```bash
mvn test
```

## Next Steps After Basic Setup

### Phase 1: Core Entities and Database Setup
1. Design and implement entity models (User, Freelancer, Client, Project, etc.)
2. Create repositories for each entity
3. Set up database migrations (consider Flyway or Liquibase)
4. Write unit tests for repositories

### Phase 2: Business Logic Layer
1. Implement service interfaces and implementations
2. Add business logic and validations
3. Write unit tests for services

### Phase 3: API Layer
1. Create REST controllers with proper endpoints
2. Implement DTOs for requests and responses
3. Add input validation
4. Write integration tests for APIs

### Phase 4: Security
1. Implement JWT-based authentication
2. Add role-based authorization
3. Configure CORS for frontend integration
4. Implement password encryption

### Phase 5: Advanced Features
1. Add exception handling with custom exceptions
2. Implement logging with proper log levels
3. Add API documentation with detailed descriptions
4. Implement pagination and sorting
5. Add search and filter capabilities

## Common Commands Reference

```bash
# Clean build
mvn clean package

# Skip tests during build
mvn clean package -DskipTests

# Run specific test class
mvn test -Dtest=UserServiceTest

# Check dependencies
mvn dependency:tree

# Update dependencies
mvn versions:display-dependency-updates

# Generate project documentation
mvn site
```

## Best Practices

1. **Layered Architecture**: Maintain clear separation between Controller, Service, and Repository layers
2. **DTOs**: Use DTOs to avoid exposing entity models directly in APIs
3. **Exception Handling**: Implement global exception handler for consistent error responses
4. **Validation**: Validate all inputs at the controller level
5. **Logging**: Use SLF4J with Logback for logging
6. **Testing**: Write unit tests for services and integration tests for controllers
7. **Documentation**: Document all APIs using OpenAPI/Swagger annotations
8. **Configuration**: Use profiles for different environments (dev, test, prod)
9. **Security**: Never commit sensitive data; use environment variables
10. **Code Quality**: Use tools like SonarLint, Checkstyle for code quality

## Troubleshooting

### Port Already in Use
```bash
# Linux/Mac
lsof -i :8080
kill -9 <PID>

# Windows
netstat -ano | findstr :8080
taskkill /PID <PID> /F
```

### Database Connection Issues
- Check database URL, username, and password
- Ensure database server is running
- Verify database driver is included in pom.xml

### Build Failures
- Run `mvn clean` to clear cached builds
- Check for dependency conflicts with `mvn dependency:tree`
- Ensure Java 17 is being used

## Resources

- [Spring Boot Documentation](https://docs.spring.io/spring-boot/docs/current/reference/html/)
- [Spring Data JPA](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/)
- [Spring Security](https://docs.spring.io/spring-security/reference/)
- [Baeldung Spring Tutorials](https://www.baeldung.com/spring-boot)

## Support

For questions or issues during setup:
1. Check Spring Boot documentation
2. Search Stack Overflow
3. Review error messages and logs carefully
4. Consult the Documents folder for project-specific requirements

---

**Ready to start?** Choose your preferred setup method and begin with Phase 1!
