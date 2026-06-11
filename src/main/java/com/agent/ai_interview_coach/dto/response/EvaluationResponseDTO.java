package com.agent.ai_interview_coach.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EvaluationResponseDTO {

    private int score;
    private String feedback;
    private String missingConcepts;
    private String correctAnswer;
    private String improvementSuggestions;
}
