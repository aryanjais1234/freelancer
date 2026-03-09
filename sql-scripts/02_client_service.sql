-- ============================================================
-- CLIENT SERVICE - clients_table
-- Database: freelancer_client
-- ============================================================

CREATE TABLE IF NOT EXISTS clients_table (
    id              SERIAL PRIMARY KEY,
    user_id         INTEGER,
    name            VARCHAR(255),
    email           VARCHAR(255),
    password        VARCHAR(255),
    company_name    VARCHAR(255),
    website         VARCHAR(500),
    bio             VARCHAR(1000),
    rating          DOUBLE PRECISION DEFAULT 0.0,
    project_ids     INTEGER[],
    created_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE UNIQUE INDEX IF NOT EXISTS idx_clients_userid ON clients_table(user_id);
CREATE UNIQUE INDEX IF NOT EXISTS idx_clients_email ON clients_table(email);

COMMENT ON TABLE clients_table IS 'Client profiles linked to user accounts';
COMMENT ON COLUMN clients_table.user_id IS 'References id in users_table (user-service DB)';
COMMENT ON COLUMN clients_table.project_ids IS 'Array of project IDs posted by this client';
