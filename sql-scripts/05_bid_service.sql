-- ============================================================
-- BID SERVICE - bids_table
-- Database: bid_service
-- ============================================================

CREATE TABLE IF NOT EXISTS bids_table (
    bid_id          SERIAL PRIMARY KEY,
    project_id      INTEGER NOT NULL,
    freelancer_id   INTEGER NOT NULL,
    proposal_text   TEXT,
    bid_amount      DOUBLE PRECISION NOT NULL,
    status          VARCHAR(50) DEFAULT 'PENDING',
    created_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE (project_id, freelancer_id)
);

CREATE INDEX IF NOT EXISTS idx_bids_projectid ON bids_table(project_id);
CREATE INDEX IF NOT EXISTS idx_bids_freelancerid ON bids_table(freelancer_id);
CREATE INDEX IF NOT EXISTS idx_bids_status ON bids_table(status);

COMMENT ON TABLE bids_table IS 'Bid/Proposal records submitted by freelancers for projects';
COMMENT ON COLUMN bids_table.status IS 'PENDING, ACCEPTED, or REJECTED';
COMMENT ON COLUMN bids_table.project_id IS 'References projects_table.id (project-service DB)';
COMMENT ON COLUMN bids_table.freelancer_id IS 'References freelancers_table.user_id (freelancer-service DB)';
