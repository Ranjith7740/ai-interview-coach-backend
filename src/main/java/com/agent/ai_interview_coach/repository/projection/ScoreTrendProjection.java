package com.agent.ai_interview_coach.repository.projection;

import java.sql.Date;

/**
 * Spring Data projection used by the native score-trend query.
 * Property names must match the column aliases in the SQL exactly.
 */
public interface ScoreTrendProjection {
    Date getDate();
    Double getAvgScore();
}
