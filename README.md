# FreelancerConnect Platform

A comprehensive freelance marketplace platform built with Spring Boot microservices backend. Clients can post projects, freelancers can browse and bid on them, and the platform manages the entire hiring workflow.

## 🏗️ Architecture

This is a **microservices** architecture built with Spring Cloud:

```
[Client]  →  [API Gateway :8765]  →  [Service Registry :8761 (Eureka)]
                     ↓
      ┌──────────────┬──────────────┬──────────────┬──────────────┐
      ▼              ▼              ▼              ▼              ▼
[User Service  [Client Service [Freelancer    [Project      [Bid Service
   :8081]         :8082]        Service        Service        :8085]
                               :8084]          :8083]
```

### Services

| Service | Port | Database | Purpose |
|---------|------|----------|---------|
| `service-registry` | 8761 | — | Eureka service discovery |
| `api-gateway` | 8765 | — | JWT authentication & routing |
| `user-service` | 8081 | `freelancer_user` | Register, login, JWT |
| `client-service` | 8082 | `freelancer_client` | Client profiles, project posting |
| `freelancer-service` | 8084 | `freelancer_db` | Freelancer profiles, project browsing |
| `project-service` | 8083 | `project_service` | Project CRUD |
| `bid-service` | 8085 | `bid_service` | Bids/proposals management |

## 🚀 Quick Start

### Prerequisites

- Java 17+
- Maven 3.9+
- PostgreSQL 15+
- Git

### 1. Set Up Databases

```bash
psql -U postgres -f sql-scripts/00_create_databases.sql
```

Or manually create:
```sql
CREATE DATABASE freelancer_user;
CREATE DATABASE freelancer_client;
CREATE DATABASE freelancer_db;
CREATE DATABASE project_service;
CREATE DATABASE bid_service;
```

Update the `password` in each service's `application.yml` to match your PostgreSQL password.

### 2. Start Services (in order)

```bash
# 1. Start Eureka Service Registry
cd backend/microservices/service-registry
mvn spring-boot:run

# 2. Start API Gateway
cd ../api-gateway
mvn spring-boot:run

# 3. Start all backend services (each in a separate terminal)
cd ../user-service       && mvn spring-boot:run
cd ../client-service     && mvn spring-boot:run
cd ../freelancer-service && mvn spring-boot:run
cd ../project-service    && mvn spring-boot:run
cd ../bid-service        && mvn spring-boot:run
```

All services auto-create their database tables (`ddl-auto: update`).

## 🔌 API Reference

All requests go through the API Gateway at `http://localhost:8765`.

### Authentication

#### Register a New User
```
POST /users/register
Content-Type: application/json

{
  "name": "John Doe",
  "userName": "john@example.com",
  "password": "securePassword123",
  "role": "FREELANCER"   // or "CLIENT"
}
```

#### Login
```
POST /users/login
Content-Type: application/json

{
  "userName": "john@example.com",
  "password": "securePassword123"
}

Response: JWT token string
```

Include JWT in all subsequent requests:
```
Authorization: Bearer <your-jwt-token>
```

---

### Client APIs (requires `ROLE_CLIENT` JWT)

#### View Client Profile
```
GET /clients/profile/{userId}
```

#### Update Client Profile
```
PUT /clients/profile/{userId}
Content-Type: application/json

{
  "companyName": "TechCorp",
  "website": "https://techcorp.com",
  "bio": "We build great products"
}
```

#### Post a Project
```
POST /clients/createProject
Authorization: Bearer <token>
Content-Type: application/json

{
  "title": "Build a REST API",
  "description": "Need a Spring Boot REST API",
  "budget": 5000,
  "budgetMin": 3000,
  "budgetMax": 7000,
  "category": "Web Development",
  "deadline": "2025-12-31",
  "status": "OPEN"
}
```

#### Get Client's Projects
```
GET /clients/projects/{userId}
Authorization: Bearer <token>
```

#### View All Bids on a Project
```
GET /bids/project/{projectId}
Authorization: Bearer <token>
```

#### Accept a Bid (Hire Freelancer)
```
PUT /bids/{bidId}/accept
Authorization: Bearer <token>
```

#### Reject a Bid
```
PUT /bids/{bidId}/reject
Authorization: Bearer <token>
```

---

### Freelancer APIs (requires `ROLE_FREELANCER` JWT)

#### View Freelancer Profile
```
GET /freelancers/profile/{userId}
```

#### Update Freelancer Profile
```
PUT /freelancers/profile/{userId}
Authorization: Bearer <token>
Content-Type: application/json

{
  "skills": "Java,Spring Boot,React",
  "hourlyRate": "50",
  "profileSummary": "Experienced Java developer",
  "availability_status": "AVAILABLE",
  "portfolioLink": "https://myportfolio.com",
  "location": "Bangalore, India"
}
```

#### Browse All Open Projects
```
GET /freelancers/projects/open
Authorization: Bearer <token>
```

#### Search Freelancers by Skill
```
GET /freelancers/search?skill=Java
Authorization: Bearer <token>
```

#### Submit a Bid on a Project
```
POST /bids/submit
Authorization: Bearer <token>
Content-Type: application/json

{
  "projectId": 1,
  "proposalText": "I can build this in 2 weeks with excellent quality",
  "bidAmount": 4500
}
```
> Note: `freelancerId` is automatically extracted from the JWT token.

#### View My Bids
```
GET /bids/freelancer/{freelancerId}
Authorization: Bearer <token>
```

---

### Project APIs (general, authenticated)

#### Get All Open Projects
```
GET /projects/open
Authorization: Bearer <token>
```

#### Get Project Details
```
GET /projects/getProject/{id}
Authorization: Bearer <token>
```

#### Get Projects by Category
```
GET /projects/category/{category}
Authorization: Bearer <token>
```

---

## 🔐 Role-Based Access Control

| Endpoint Pattern | Allowed Role |
|-----------------|--------------|
| `POST /users/register` | Public |
| `POST /users/login` | Public |
| `GET/POST /clients/**` | ROLE_CLIENT |
| `GET/POST /freelancers/**` | ROLE_FREELANCER |
| `POST /bids/submit` | ROLE_FREELANCER |
| `GET /bids/freelancer/**` | ROLE_FREELANCER |
| `GET /bids/project/**` | ROLE_CLIENT |
| `PUT /bids/*/accept` | ROLE_CLIENT |
| `PUT /bids/*/reject` | ROLE_CLIENT |
| `GET /projects/**` | Authenticated |

## 📁 Project Structure

```
freelancer/
├── backend/
│   └── microservices/
│       ├── service-registry/     # Eureka server (port 8761)
│       ├── api-gateway/          # Spring Cloud Gateway + JWT (port 8765)
│       ├── user-service/         # Registration & login (port 8081)
│       ├── client-service/       # Client profiles & projects (port 8082)
│       ├── project-service/      # Project management (port 8083)
│       ├── freelancer-service/   # Freelancer profiles (port 8084)
│       └── bid-service/          # Bid/proposal management (port 8085)
├── sql-scripts/                  # All DDL & sample SQL scripts
└── Documents/                    # BRD, SRS, LLD, ER Diagram, etc.
```

## 🗄️ Database Schema

SQL scripts are in the [`sql-scripts/`](sql-scripts/) folder:

| Script | Description |
|--------|-------------|
| `00_create_databases.sql` | Create all PostgreSQL databases |
| `01_user_service.sql` | `users_table` schema |
| `02_client_service.sql` | `clients_table` schema |
| `03_freelancer_service.sql` | `freelancers_table` schema |
| `04_project_service.sql` | `projects_table` schema |
| `05_bid_service.sql` | `bids_table` schema |
| `06_sample_data.sql` | Sample test data |

## 🔄 Typical User Flow

1. **Register** → `POST /users/register` (choose FREELANCER or CLIENT role)
2. **Login** → `POST /users/login` (get JWT token)
3. **Client posts project** → `POST /clients/createProject`
4. **Freelancer browses projects** → `GET /freelancers/projects/open`
5. **Freelancer submits bid** → `POST /bids/submit`
6. **Client reviews bids** → `GET /bids/project/{projectId}`
7. **Client hires freelancer** → `PUT /bids/{bidId}/accept`
   - All other bids for that project are automatically rejected
   - Project status changes to `IN_PROGRESS`

## 🛠️ Technology Stack

| Layer | Technology |
|-------|-----------|
| Language | Java 17 |
| Framework | Spring Boot 3.x |
| Service Discovery | Netflix Eureka |
| API Gateway | Spring Cloud Gateway |
| Authentication | JWT (JJWT 0.11.5) |
| Inter-service Comms | OpenFeign |
| Database | PostgreSQL |
| ORM | Spring Data JPA (Hibernate) |
| Build | Maven |
| Utilities | Lombok |

## 📚 Documentation

Full project documentation is in the [`Documents/`](Documents/) folder:

- **BRD.docx** – Business Requirements Document
- **SRSdocx.docx** – Software Requirements Specification
- **SDD.docx** – System Design Document
- **LLD.docx** – Low-Level Design
- **ER (Entity-Relationship) Diagram.docx** – Database ER diagram
- **Architecture Diagram with all API endpoints.docx** – Full API architecture
- **Use Case Document.docx** – Use cases
- **Test Plan and Test Cases.docx** – Testing strategy

## ⚙️ Configuration

Default credentials in `application.yml` files:
```yaml
datasource:
  username: postgres
  password: aryan
```

Update these to match your local PostgreSQL setup.

## 📝 License

[Specify your license here]

