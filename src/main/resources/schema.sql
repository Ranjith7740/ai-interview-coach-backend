-- ─────────────────────────────────────────────────────────────────────────────
-- AI Interview Coach — Phase 2 Schema
-- Database: interview_coach_db
-- ─────────────────────────────────────────────────────────────────────────────

CREATE TABLE IF NOT EXISTS interview_sessions (
    id                      BIGINT          NOT NULL AUTO_INCREMENT,
    skill                   VARCHAR(100)    NULL,
    question                TEXT            NOT NULL,
    answer                  TEXT            NOT NULL,
    score                   INT             NOT NULL,
    feedback                TEXT            NULL,
    missing_concepts        TEXT            NULL,
    correct_answer          TEXT            NULL,
    improvement_suggestions TEXT            NULL,

    -- Audit
    created_at              DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP,

    -- user_id BIGINT NULL,  -- reserved: uncomment when multi-user support is added

    CONSTRAINT pk_interview_sessions PRIMARY KEY (id)
);

-- Indexes for common query patterns
CREATE INDEX IF NOT EXISTS idx_is_skill       ON interview_sessions (skill);
CREATE INDEX IF NOT EXISTS idx_is_created_at  ON interview_sessions (created_at);
CREATE INDEX IF NOT EXISTS idx_is_score       ON interview_sessions (score);
