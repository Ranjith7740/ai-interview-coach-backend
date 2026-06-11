package com.agent.ai_interview_coach.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class EvaluationRequestDTO {

    @NotBlank(message = "Question must not be blank")
    @Size(min = 10, max = 1000, message = "Question must be between 10 and 1000 characters")
    private String question;

    @NotBlank(message = "Answer must not be blank")
    @Size(min = 5, max = 5000, message = "Answer must be between 5 and 5000 characters")
    private String answer;
}
