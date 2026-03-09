-- ============================================================
-- SAMPLE DATA for testing the FreelancerConnect platform
-- Run AFTER tables have been created (either manually or via ddl-auto: update)
-- ============================================================

-- NOTE: User passwords must be BCrypt-encoded before inserting.
-- The values below use BCrypt hash for "password123"
-- Hash: $2a$10$SomeExampleHashHere (generate with BCryptPasswordEncoder)

-- ==== USER SERVICE (freelancer_user) ====

-- Insert sample users (role: 0=FREELANCER, 1=CLIENT)
INSERT INTO users_table (name, user_name, password, role)
VALUES
  ('Alice Freelancer', 'alice@example.com', '$2a$10$exampleHashedPassword', 0),
  ('Bob Client',       'bob@example.com',   '$2a$10$exampleHashedPassword', 1)
ON CONFLICT (user_name) DO NOTHING;

-- ==== FREELANCER SERVICE (freelancer_db) ====

-- Insert sample freelancer profile (user_id must match inserted user above)
INSERT INTO freelancers_table (user_id, name, email, skills, availability_status, hourly_rate)
VALUES (1, 'Alice Freelancer', 'alice@example.com', 'Java,Spring Boot,React', 'AVAILABLE', '50')
ON CONFLICT (user_id) DO NOTHING;

-- ==== CLIENT SERVICE (freelancer_client) ====

-- Insert sample client profile (user_id must match inserted user above)
INSERT INTO clients_table (user_id, name, email, company_name, bio)
VALUES (2, 'Bob Client', 'bob@example.com', 'TechCorp', 'Looking for talented developers')
ON CONFLICT (user_id) DO NOTHING;

-- ==== PROJECT SERVICE (project_service) ====

-- Insert sample project
INSERT INTO projects_table (client_id, title, description, budget, budget_min, budget_max, category, status)
VALUES (1, 'Build a REST API', 'Need a Spring Boot REST API for inventory management',
        5000, 3000, 7000, 'Web Development', 'OPEN')
ON CONFLICT DO NOTHING;
