package com.agent.ai_interview_coach.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

/**
 * Aggregated dashboard statistics.
 * bestSkill / weakestSkill are null when fewer than 2 distinct skills exist.
 */
@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DashboardStatsDTO {

    private long totalInterviews;
    private double averageScore;
    private String bestSkill;
    private String weakestSkill;
}
