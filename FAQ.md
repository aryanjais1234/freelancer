# Frequently Asked Questions (FAQ)

## Getting Started

### Q1: Where do I start?
**A:** Follow these steps in order:
1. Read [QUICK_START.md](QUICK_START.md) for the fastest path to a working application
2. Review [TECH_STACK.md](TECH_STACK.md) to understand technology choices
3. Use [BACKEND_SETUP_GUIDE.md](BACKEND_SETUP_GUIDE.md) for detailed implementation guidance

### Q2: What tools do I need installed?
**A:** You need:
- Java 17 or higher (already available: Java 17.0.16)
- Maven 3.9.x or higher (already available: Maven 3.9.11)
- Your favorite IDE (IntelliJ IDEA recommended, but Eclipse or VS Code work too)
- Git (for version control)
- Optional: PostgreSQL or MySQL for production database

### Q3: Should I use Spring Initializr or Maven commands?
**A:** For beginners, **use Spring Initializr** (https://start.spring.io/). It's:
- User-friendly with a web interface
- Automatically handles dependency versions
- Pre-configures your project structure
- Takes less than 2 minutes

## Project Structure

### Q4: What package structure should I use?
**A:** Use a layered architecture:
```
com.freelancer/
├── controller/     # REST endpoints
├── service/        # Business logic
├── repository/     # Database access
├── model/          # Entity classes
├── dto/            # Data transfer objects
├── config/         # Configuration classes
├── exception/      # Custom exceptions
└── util/           # Utility classes
```

### Q5: Should I create a separate backend folder or use the root?
**A:** Two options:
- **Option 1**: Create `/freelancer-backend` folder in root (recommended for monorepo with future frontend)
- **Option 2**: Use root directory if backend-only project

### Q6: Where do I put application.yml?
**A:** In `src/main/resources/application.yml` within your Spring Boot project. Use the template from `config-templates/application.yml`.

## Database

### Q7: Should I use H2, PostgreSQL, or MySQL?
**A:** 
- **Development**: Use H2 (in-memory, no installation needed)
- **Production**: Use PostgreSQL (recommended) or MySQL
- You can easily switch between them using Spring profiles

### Q8: Do I need to install a database right away?
**A:** No! Start with H2 database (in-memory). It requires zero installation and is perfect for development. Switch to PostgreSQL/MySQL later.

### Q9: How do I switch from H2 to PostgreSQL?
**A:** 
1. Install PostgreSQL
2. Create a database
3. Update `application-prod.yml` with PostgreSQL connection details
4. Run with `--spring.profiles.active=prod` profile

## Development

### Q10: How do I run the application?
**A:**
```bash
# Method 1: Using Maven
mvn spring-boot:run

# Method 2: Run the JAR
mvn clean package
java -jar target/freelancer-backend-0.0.1-SNAPSHOT.jar

# Method 3: From IDE
Right-click FreelancerApplication.java -> Run
```

### Q11: How do I test if it's working?
**A:**
1. Check console output for "Started FreelancerApplication"
2. Visit http://localhost:8080/swagger-ui.html
3. Visit http://localhost:8080/api/health (if you create a health endpoint)
4. Visit http://localhost:8080/h2-console (for H2 database console)

### Q12: Can I change the port from 8080?
**A:** Yes, in `application.yml`:
```yaml
server:
  port: 9090  # or any available port
```

### Q13: How do I enable hot reload?
**A:** Add Spring Boot DevTools dependency (included in pom.xml.template). It automatically restarts the application when you save changes.

## Dependencies

### Q14: What's the difference between Spring Boot starters?
**A:**
- **spring-boot-starter-web**: REST APIs, HTTP, Jackson JSON
- **spring-boot-starter-data-jpa**: Database access with JPA/Hibernate
- **spring-boot-starter-security**: Authentication & authorization
- **spring-boot-starter-validation**: Input validation annotations

### Q15: Do I need all the dependencies in pom.xml.template?
**A:** No. Start minimal:
- spring-boot-starter-web (required)
- spring-boot-starter-data-jpa (required for database)
- H2 database (for development)
- Lombok (optional but highly recommended)
- SpringDoc OpenAPI (for API documentation)

Add others as needed.

### Q16: What is Lombok and do I need it?
**A:** Lombok reduces boilerplate code. Instead of writing getters/setters/constructors manually, you use annotations:
```java
@Data  // Generates getters, setters, toString, etc.
public class User {
    private Long id;
    private String name;
}
```
Highly recommended but optional.

## Security

### Q17: Do I need to implement security from day one?
**A:** No. Build basic functionality first:
1. Create entities and repositories
2. Build service layer
3. Create REST APIs
4. Add security later

However, the Spring Security dependency is included by default. You can temporarily disable it or create a basic configuration.

### Q18: How do I disable Spring Security temporarily?
**A:** Either:
1. Remove spring-boot-starter-security from pom.xml, OR
2. Create a config that permits all requests:
```java
@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) {
        http.authorizeHttpRequests(auth -> auth.anyRequest().permitAll());
        return http.build();
    }
}
```

### Q19: What is JWT and when should I implement it?
**A:** JWT (JSON Web Token) is for authentication. Implement it in Phase 4, after basic APIs are working. It's not needed initially.

## API Documentation

### Q20: What is Swagger and how do I access it?
**A:** Swagger (OpenAPI) provides interactive API documentation. Access it at:
- Swagger UI: http://localhost:8080/swagger-ui.html
- API Docs JSON: http://localhost:8080/api-docs

### Q21: Do I need to write documentation manually?
**A:** No! SpringDoc OpenAPI auto-generates documentation from your code. You can add annotations for better descriptions:
```java
@Operation(summary = "Get user by ID")
@GetMapping("/users/{id}")
public User getUser(@PathVariable Long id) { ... }
```

## Testing

### Q22: Should I write tests from the beginning?
**A:** Best practice: Yes, but for learning, you can:
1. Build basic functionality first
2. Test manually with Swagger/Postman
3. Add automated tests later

### Q23: What testing tools should I use?
**A:** Spring Boot includes:
- JUnit 5 (unit testing)
- Mockito (mocking)
- Spring Boot Test (integration testing)

All are already configured in spring-boot-starter-test.

## Common Issues

### Q24: Port 8080 is already in use, what do I do?
**A:**
```bash
# Find process using port 8080
lsof -i :8080
# Kill it
kill -9 <PID>

# Or change the port in application.yml
server:
  port: 9090
```

### Q25: I get "java: package lombok does not exist"
**A:** Your IDE needs Lombok plugin:
- IntelliJ: Install Lombok plugin from marketplace
- Eclipse: Run lombok.jar as admin
- VS Code: Install Lombok Annotations Support

### Q26: Build fails with dependency errors
**A:**
```bash
# Clear Maven cache and rebuild
mvn clean
mvn dependency:purge-local-repository
mvn clean install
```

### Q27: Application starts but APIs return 404
**A:** Check:
1. Is your controller package under com.freelancer?
2. Did you use @RestController annotation?
3. Is the path correct? (server.servlet.context-path in yml)

### Q28: Database connection fails
**A:** For H2, this shouldn't happen. If using PostgreSQL/MySQL:
1. Is the database server running?
2. Is the database created?
3. Are credentials correct in application.yml?
4. Is the driver included in pom.xml?

## Best Practices

### Q29: What's the recommended development workflow?
**A:**
1. Create entity class
2. Create repository interface
3. Create service interface and implementation
4. Create controller
5. Test with Swagger
6. Write unit tests
7. Commit to Git

### Q30: Should I commit application.yml with passwords?
**A:** **NEVER!** Use:
- Environment variables for sensitive data
- application-local.yml (add to .gitignore)
- Profiles for different environments

### Q31: How do I organize DTOs?
**A:**
```
dto/
├── request/
│   ├── LoginRequest.java
│   ├── RegisterRequest.java
│   └── CreateProjectRequest.java
└── response/
    ├── UserResponse.java
    ├── ProjectResponse.java
    └── ApiResponse.java
```

### Q32: Should I validate inputs?
**A:** YES! Always validate at the controller level:
```java
@PostMapping("/users")
public User create(@Valid @RequestBody CreateUserRequest request) {
    // @Valid triggers validation
}
```

## Next Steps

### Q33: I've set up the basic project. What's next?
**A:** Follow this sequence:
1. Review Documents folder for requirements
2. Design your entities based on ER diagram
3. Implement User entity and authentication
4. Build core business entities (Project, Freelancer, etc.)
5. Create REST APIs for each entity
6. Add security
7. Write tests

### Q34: Where can I learn more about Spring Boot?
**A:**
- Official Docs: https://spring.io/projects/spring-boot
- Baeldung: https://www.baeldung.com/spring-boot
- Spring Guides: https://spring.io/guides

### Q35: How do I deploy this to production?
**A:** See "Deployment Plan.docx" in Documents folder. General steps:
1. Build JAR: `mvn clean package`
2. Set production profile: `--spring.profiles.active=prod`
3. Set environment variables for secrets
4. Deploy to cloud (AWS, Azure, Heroku) or VPS

## Advanced Topics

### Q36: When should I add Redis caching?
**A:** After basic functionality works and you identify performance bottlenecks. Not needed initially.

### Q37: Should I use Flyway or Liquibase for migrations?
**A:** Add in Phase 2/3 when your schema stabilizes:
- **Flyway**: Simpler, SQL-based
- **Liquibase**: More features, supports multiple formats

Choose based on team familiarity.

### Q38: How do I handle file uploads?
**A:** Add configuration in application.yml:
```yaml
spring:
  servlet:
    multipart:
      max-file-size: 10MB
      max-request-size: 10MB
```
Then use `MultipartFile` in controller.

### Q39: Should I use microservices architecture?
**A:** Start with monolith (single application). Move to microservices only when:
- Team grows significantly
- Different parts need independent scaling
- You have infrastructure for managing multiple services

### Q40: How do I add email functionality?
**A:** Add spring-boot-starter-mail dependency and configure SMTP settings. Implement after core functionality is complete.

---

## Still have questions?

1. Check the comprehensive guides:
   - [BACKEND_SETUP_GUIDE.md](BACKEND_SETUP_GUIDE.md)
   - [QUICK_START.md](QUICK_START.md)
   - [TECH_STACK.md](TECH_STACK.md)

2. Search documentation:
   - Spring Boot: https://docs.spring.io/spring-boot/docs/current/reference/html/
   - Stack Overflow: Tag your search with [spring-boot]

3. Review Documents folder for project-specific requirements

4. Common error messages usually have good solutions on Stack Overflow

---

**Pro Tip**: Start simple, build incrementally, test frequently, commit often!
