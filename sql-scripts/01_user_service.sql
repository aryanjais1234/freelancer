-- ============================================================
-- USER SERVICE - users_table
-- Database: freelancer_user
-- ============================================================

CREATE TABLE IF NOT EXISTS users_table (
    id          SERIAL PRIMARY KEY,
    name        VARCHAR(255) NOT NULL UNIQUE,
    user_name   VARCHAR(255) NOT NULL UNIQUE,
    password    VARCHAR(255) NOT NULL,
    role        INTEGER NOT NULL,   -- 0 = FREELANCER, 1 = CLIENT
    created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Index for fast lookup by email (user_name)
CREATE UNIQUE INDEX IF NOT EXISTS idx_users_username ON users_table(user_name);

COMMENT ON TABLE users_table IS 'Core user accounts for the FreelancerConnect platform';
COMMENT ON COLUMN users_table.role IS '0 = FREELANCER, 1 = CLIENT';
