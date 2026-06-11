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

    CONSTRAINT pk_interview_sessions PRIMARY KEY (id),

    -- Indexes inline (Safest approach for MySQL schema initialization)
    INDEX idx_is_skill (skill),
    INDEX idx_is_created_at (created_at),
    INDEX idx_is_score (score)
);