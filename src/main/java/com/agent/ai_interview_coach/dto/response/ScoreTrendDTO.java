package com.agent.ai_interview_coach.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

/**
 * Average score per calendar day for the trend chart.
 */
@Data
@AllArgsConstructor
public class ScoreTrendDTO {

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    private double averageScore;
}
