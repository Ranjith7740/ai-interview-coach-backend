package com.agent.ai_interview_coach.mapper;

import com.agent.ai_interview_coach.dto.request.EvaluationRequestDTO;
import com.agent.ai_interview_coach.dto.response.EvaluationResponseDTO;
import com.agent.ai_interview_coach.dto.response.InterviewDetailDTO;
import com.agent.ai_interview_coach.dto.response.InterviewHistoryDTO;
import com.agent.ai_interview_coach.entity.InterviewSession;
import org.springframework.stereotype.Component;

/**
 * Manual mapper — keeps entity and DTO fully decoupled with no reflection
 * or code-generation overhead at runtime.
 */
@Component
public class InterviewSessionMapper {

    /**
     * Builds a new (un-persisted) entity from the evaluation request + result.
     */
    public InterviewSession toEntity(EvaluationRequestDTO request, EvaluationResponseDTO result) {
        return InterviewSession.builder()
                .skill(request.getSkill())
                .question(request.getQuestion())
                .answer(request.getAnswer())
                .score(result.getScore())
                .feedback(result.getFeedback())
                .missingConcepts(result.getMissingConcepts())
                .correctAnswer(result.getCorrectAnswer())
                .improvementSuggestions(result.getImprovementSuggestions())
                .build();
    }

    /**
     * Lightweight summary for the paginated history list.
     */
    public InterviewHistoryDTO toHistoryDTO(InterviewSession session) {
        return InterviewHistoryDTO.builder()
                .id(session.getId())
                .skill(session.getSkill())
                .score(session.getScore())
                .createdAt(session.getCreatedAt() != null
                        ? session.getCreatedAt().toLocalDate()
                        : null)
                .build();
    }

    /**
     * Full detail including all stored evaluation fields.
     */
    public InterviewDetailDTO toDetailDTO(InterviewSession session) {
        return InterviewDetailDTO.builder()
                .id(session.getId())
                .skill(session.getSkill())
                .question(session.getQuestion())
                .answer(session.getAnswer())
                .score(session.getScore())
                .feedback(session.getFeedback())
                .missingConcepts(session.getMissingConcepts())
                .correctAnswer(session.getCorrectAnswer())
                .improvementSuggestions(session.getImprovementSuggestions())
                .createdAt(session.getCreatedAt())
                .build();
    }
}
