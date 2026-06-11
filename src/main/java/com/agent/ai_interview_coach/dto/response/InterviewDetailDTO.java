package com.agent.ai_interview_coach.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Full session detail including all fields returned by the Gemini evaluation.
 */
@Data
@Builder
public class InterviewDetailDTO {

    private Long id;
    private String skill;
    private String question;
    private String answer;
    private Integer score;
    private String feedback;
    private String missingConcepts;
    private String correctAnswer;
    private String improvementSuggestions;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
}
