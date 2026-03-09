# SQL Scripts - FreelancerConnect Platform

This folder contains all SQL scripts for the FreelancerConnect microservices.

## Scripts Overview

| File | Purpose | Database |
|------|---------|----------|
| `00_create_databases.sql` | Create all required PostgreSQL databases | postgres (superuser) |
| `01_user_service.sql` | DDL for `users_table` | `freelancer_user` |
| `02_client_service.sql` | DDL for `clients_table` | `freelancer_client` |
| `03_freelancer_service.sql` | DDL for `freelancers_table` | `freelancer_db` |
| `04_project_service.sql` | DDL for `projects_table` | `project_service` |
| `05_bid_service.sql` | DDL for `bids_table` | `bid_service` |
| `06_sample_data.sql` | Sample data for testing | All databases |

## How to Run

### Step 1: Create databases
```bash
psql -U postgres -f 00_create_databases.sql
```

### Step 2: Create tables (run per database)
```bash
psql -U postgres -d freelancer_user    -f 01_user_service.sql
psql -U postgres -d freelancer_client  -f 02_client_service.sql
psql -U postgres -d freelancer_db      -f 03_freelancer_service.sql
psql -U postgres -d project_service    -f 04_project_service.sql
psql -U postgres -d bid_service        -f 05_bid_service.sql
```

### Step 3: (Optional) Load sample data
```bash
# User service database
psql -U postgres -d freelancer_user -c "INSERT INTO users_table (name, user_name, password, role) VALUES ('Alice Freelancer', 'alice@example.com', '\$2a\$10\$exampleHash', 0) ON CONFLICT (user_name) DO NOTHING;"

# Or run the full sample data file against each relevant database
psql -U postgres -d freelancer_db      -f 06_sample_data.sql
psql -U postgres -d freelancer_client  -f 06_sample_data.sql
psql -U postgres -d project_service    -f 06_sample_data.sql
```

## Notes

- All services use `spring.jpa.hibernate.ddl-auto: update`, so tables are created automatically when services start.
- These SQL scripts are provided for reference, documentation, and manual setup.
- All `TIMESTAMP` columns are managed by Hibernate's `@CreationTimestamp` and `@UpdateTimestamp`.
