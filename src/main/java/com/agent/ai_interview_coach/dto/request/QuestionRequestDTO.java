package com.agent.ai_interview_coach.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class QuestionRequestDTO {

    @NotBlank(message = "Skill must not be blank")
    @Size(min = 2, max = 100, message = "Skill must be between 2 and 100 characters")
    private String skill;
}
