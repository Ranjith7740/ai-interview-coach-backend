package com.agent.ai_interview_coach.service;

import com.agent.ai_interview_coach.dto.request.EvaluationRequestDTO;
import com.agent.ai_interview_coach.dto.response.*;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface InterviewSessionService {

    /**
     * Persists a completed interview session.
     * Called by the AOP aspect after every successful evaluation.
     */
    void saveSession(EvaluationRequestDTO request, EvaluationResponseDTO result);

    /**
     * Paginated list of all sessions ordered by any Pageable spec.
     */
    PagedResponse<InterviewHistoryDTO> getHistory(Pageable pageable);

    /**
     * Full detail for a single session.
     *
     * @throws com.agent.ai_interview_coach.exception.ResourceNotFoundException when id not found
     */
    InterviewDetailDTO getById(Long id);

    /**
     * Aggregated stats: total count, average score, best/weakest skill.
     */
    DashboardStatsDTO getDashboardStats();

    /**
     * Average score grouped by calendar day, ascending.
     */
    List<ScoreTrendDTO> getScoreTrend();
}
