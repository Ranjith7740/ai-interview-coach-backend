package com.agent.ai_interview_coach.service;

import com.agent.ai_interview_coach.dto.request.EvaluationRequestDTO;
import com.agent.ai_interview_coach.dto.request.QuestionRequestDTO;
import com.agent.ai_interview_coach.dto.response.EvaluationResponseDTO;
import com.agent.ai_interview_coach.dto.response.QuestionResponseDTO;

public interface InterviewService {

    QuestionResponseDTO generateQuestion(QuestionRequestDTO request);

    EvaluationResponseDTO evaluateAnswer(EvaluationRequestDTO request);
}
