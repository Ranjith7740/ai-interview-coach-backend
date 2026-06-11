package com.agent.ai_interview_coach.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

/**
 * Lightweight summary for the paginated history list.
 * Deliberately excludes question/answer/feedback to keep the list lean.
 */
@Data
@Builder
public class InterviewHistoryDTO {

    private Long id;
    private String skill;
    private Integer score;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate createdAt;
}
