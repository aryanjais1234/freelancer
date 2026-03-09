-- ============================================================
-- FREELANCER SERVICE - freelancers_table
-- Database: freelancer_db
-- ============================================================

CREATE TABLE IF NOT EXISTS freelancers_table (
    freelancer_id           SERIAL PRIMARY KEY,
    user_id                 INTEGER,
    name                    VARCHAR(255),
    email                   VARCHAR(255),
    password                VARCHAR(255),
    skills                  TEXT,
    rating                  DOUBLE PRECISION DEFAULT 0.0,
    completed_projects      INTEGER DEFAULT 0,
    availability_status     VARCHAR(50) DEFAULT 'AVAILABLE',
    profile_summary         TEXT,
    portfolio_link          VARCHAR(500),
    location                VARCHAR(255),
    profile_picture_url     VARCHAR(500),
    phone_number            VARCHAR(50),
    social_links            TEXT,
    certifications          TEXT,
    languages               TEXT,
    hourly_rate             VARCHAR(100),
    education               TEXT,
    work_experience         TEXT,
    additional_info         TEXT,
    created_at              TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at              TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE UNIQUE INDEX IF NOT EXISTS idx_freelancers_userid ON freelancers_table(user_id);

COMMENT ON TABLE freelancers_table IS 'Freelancer profiles linked to user accounts';
COMMENT ON COLUMN freelancers_table.skills IS 'Comma-separated list of skills (e.g., Java,React,Spring Boot)';
COMMENT ON COLUMN freelancers_table.availability_status IS 'AVAILABLE, BUSY, or UNAVAILABLE';
