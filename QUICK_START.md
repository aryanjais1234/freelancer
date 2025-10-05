# Quick Start Guide - Freelancer Backend

## 🚀 Get Started in 3 Steps

### Step 1: Generate Spring Boot Project

**Easiest Method - Using Spring Initializr Web:**
1. Visit: https://start.spring.io/
2. Use these settings:
   - Project: **Maven**
   - Language: **Java**
   - Spring Boot: **3.2.x** (latest)
   - Group: **com.freelancer**
   - Artifact: **freelancer-backend**
   - Java: **17**
3. Add dependencies:
   - Spring Web
   - Spring Data JPA
   - Spring Security
   - Lombok
   - PostgreSQL Driver
   - H2 Database
   - Spring Boot DevTools
   - SpringDoc OpenAPI UI
4. Click **GENERATE**
5. Extract and copy to repository

### Step 2: Build and Run

```bash
# Navigate to project directory
cd freelancer-backend

# Build the project
mvn clean install

# Run the application
mvn spring-boot:run
```

### Step 3: Verify Installation

```bash
# Test health endpoint
curl http://localhost:8080/actuator/health

# Open Swagger UI in browser
http://localhost:8080/swagger-ui.html
```

---

## 📋 What You Have Now

After completing the above steps, you will have:

✅ A working Spring Boot application
✅ Maven build system configured
✅ Basic project structure with proper packages
✅ Spring Web for REST APIs
✅ Spring Data JPA for database operations
✅ Spring Security (can be configured later)
✅ API documentation with Swagger
✅ Development tools for hot reload

---

## 🏗️ Recommended Project Structure

Once generated, your project will have:

```
freelancer-backend/
├── src/
│   ├── main/
│   │   ├── java/com/freelancer/
│   │   │   └── FreelancerBackendApplication.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/
├── pom.xml
└── README.md
```

You should organize it into:

```
src/main/java/com/freelancer/
├── FreelancerBackendApplication.java
├── controller/     # REST API endpoints
├── service/        # Business logic
├── repository/     # Database access
├── model/          # Entity classes
├── dto/            # Data transfer objects
├── config/         # Configuration classes
├── exception/      # Custom exceptions
└── util/           # Utility classes
```

---

## 🎯 Your Next Actions

### Immediate (After Project Generation):
1. ✅ Generate project from Spring Initializr
2. ✅ Copy to your repository (root or create backend folder)
3. ✅ Run `mvn clean install`
4. ✅ Run `mvn spring-boot:run`
5. ✅ Verify application starts on port 8080

### Phase 1 - Database & Models:
- Design your entity models based on Documents/ER Diagram
- Create JPA entity classes (User, Project, Freelancer, etc.)
- Create repository interfaces
- Configure database connection

### Phase 2 - Business Logic:
- Create service interfaces
- Implement service classes with business logic
- Add validation logic

### Phase 3 - REST APIs:
- Create controllers
- Define API endpoints
- Implement request/response DTOs
- Test APIs with Swagger

### Phase 4 - Security:
- Configure Spring Security
- Implement JWT authentication
- Add role-based authorization

---

## 📝 Configuration Template

After generation, update `application.properties` or create `application.yml`:

```yaml
spring:
  application:
    name: freelancer-backend
  datasource:
    url: jdbc:h2:mem:testdb
    driver-class-name: org.h2.Driver
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
  h2:
    console:
      enabled: true

server:
  port: 8080
```

---

## 🛠️ Essential Maven Commands

```bash
# Build project
mvn clean install

# Run application
mvn spring-boot:run

# Run tests
mvn test

# Package as JAR
mvn clean package

# Skip tests during build
mvn clean install -DskipTests
```

---

## 🔍 Verify Setup

After running the application, check:

1. **Console Output**: Should show "Started FreelancerBackendApplication in X seconds"
2. **Health Check**: `curl http://localhost:8080/actuator/health`
3. **Swagger UI**: Open `http://localhost:8080/swagger-ui.html` in browser
4. **H2 Console**: Open `http://localhost:8080/h2-console` (if using H2)

---

## ⚠️ Common Issues & Solutions

**Issue**: Port 8080 already in use
```bash
# Find and kill the process
lsof -i :8080
kill -9 <PID>
```

**Issue**: Java version mismatch
```bash
# Check Java version
java -version
# Should show Java 17
```

**Issue**: Maven command not found
```bash
# Check Maven installation
mvn -version
```

---

## 📚 Additional Resources

- Full Setup Guide: See `BACKEND_SETUP_GUIDE.md`
- Spring Boot Docs: https://spring.io/projects/spring-boot
- Spring Initializr: https://start.spring.io/

---

## 💡 Pro Tips

1. **Use Spring Initializr** for fastest setup - it handles all dependency versions
2. **Start with H2 database** for development - switch to PostgreSQL/MySQL later
3. **Enable Spring DevTools** for automatic restart on code changes
4. **Use Lombok** to reduce boilerplate code
5. **Keep it simple** initially - add complexity gradually

---

**Ready?** Generate your project and start coding! 🎉

For detailed guidance, refer to `BACKEND_SETUP_GUIDE.md`
