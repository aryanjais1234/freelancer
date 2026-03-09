-- ============================================================
-- PROJECT SERVICE - projects_table
-- Database: project_service
-- ============================================================

CREATE TABLE IF NOT EXISTS projects_table (
    id                      SERIAL PRIMARY KEY,
    client_id               INTEGER NOT NULL,
    title                   VARCHAR(500) NOT NULL,
    description             TEXT,
    budget                  DOUBLE PRECISION,
    budget_min              DOUBLE PRECISION,
    budget_max              DOUBLE PRECISION,
    category                VARCHAR(255),
    deadline                DATE,
    duration                BIGINT,     -- stored in nanoseconds by Hibernate (java.time.Duration)
    status                  VARCHAR(50) DEFAULT 'OPEN',
    assigned_freelancer_id  INTEGER,
    created_at              TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at              TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX IF NOT EXISTS idx_projects_clientid ON projects_table(client_id);
CREATE INDEX IF NOT EXISTS idx_projects_status ON projects_table(status);
CREATE INDEX IF NOT EXISTS idx_projects_category ON projects_table(LOWER(category));

COMMENT ON TABLE projects_table IS 'Projects posted by clients, available for freelancers to bid on';
COMMENT ON COLUMN projects_table.status IS 'OPEN, IN_PROGRESS, or COMPLETED';
COMMENT ON COLUMN projects_table.assigned_freelancer_id IS 'Freelancer ID assigned after bid acceptance; references freelancers_table.user_id';
