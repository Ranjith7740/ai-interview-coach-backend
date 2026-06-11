package com.agent.ai_interview_coach.service.impl;

import com.agent.ai_interview_coach.dto.request.EvaluationRequestDTO;
import com.agent.ai_interview_coach.dto.response.*;
import com.agent.ai_interview_coach.entity.InterviewSession;
import com.agent.ai_interview_coach.exception.ResourceNotFoundException;
import com.agent.ai_interview_coach.mapper.InterviewSessionMapper;
import com.agent.ai_interview_coach.repository.InterviewSessionRepository;
import com.agent.ai_interview_coach.service.InterviewSessionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class InterviewSessionServiceImpl implements InterviewSessionService {

    private final InterviewSessionRepository sessionRepository;
    private final InterviewSessionMapper mapper;

    // ── Save ─────────────────────────────────────────────────────────────────

    @Override
    @Transactional
    public void saveSession(EvaluationRequestDTO request, EvaluationResponseDTO result) {
        InterviewSession session = mapper.toEntity(request, result);
        InterviewSession saved = sessionRepository.save(session);
        log.info("Saved interview session id={}, skill={}, score={}",
                saved.getId(), saved.getSkill(), saved.getScore());
    }

    // ── History ───────────────────────────────────────────────────────────────

    @Override
    @Transactional(readOnly = true)
    public PagedResponse<InterviewHistoryDTO> getHistory(Pageable pageable) {
        log.info("Fetching interview history — page={}, size={}", pageable.getPageNumber(), pageable.getPageSize());
        Page<InterviewHistoryDTO> page = sessionRepository.findAll(pageable)
                .map(mapper::toHistoryDTO);
        return PagedResponse.of(page);
    }

    // ── Detail ────────────────────────────────────────────────────────────────

    @Override
    @Transactional(readOnly = true)
    public InterviewDetailDTO getById(Long id) {
        log.info("Fetching interview session id={}", id);
        InterviewSession session = sessionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("InterviewSession", id));
        return mapper.toDetailDTO(session);
    }

    // ── Dashboard Stats ───────────────────────────────────────────────────────

    @Override
    @Transactional(readOnly = true)
    public DashboardStatsDTO getDashboardStats() {
        log.info("Computing dashboard stats");

        long total = sessionRepository.count();
        Double avg = sessionRepository.findAverageScore();
        double averageScore = (avg != null) ? Math.round(avg * 100.0) / 100.0 : 0.0;

        // Best and weakest skill require at least one session with a skill value
        List<String> topSkills    = sessionRepository.findSkillsOrderedByAvgScoreDesc(PageRequest.of(0, 1));
        List<String> bottomSkills = sessionRepository.findSkillsOrderedByAvgScoreAsc(PageRequest.of(0, 1));

        String bestSkill    = topSkills.isEmpty()    ? null : topSkills.get(0);
        String weakestSkill = bottomSkills.isEmpty() ? null : bottomSkills.get(0);

        // If only one distinct skill exists, best == weakest — surface that honestly
        if (bestSkill != null && bestSkill.equalsIgnoreCase(weakestSkill)) {
            weakestSkill = null;
        }

        return DashboardStatsDTO.builder()
                .totalInterviews(total)
                .averageScore(averageScore)
                .bestSkill(bestSkill)
                .weakestSkill(weakestSkill)
                .build();
    }

    // ── Score Trend ───────────────────────────────────────────────────────────

    @Override
    @Transactional(readOnly = true)
    public List<ScoreTrendDTO> getScoreTrend() {
        log.info("Fetching score trend data");
        return sessionRepository.findScoreTrend().stream()
                .map(p -> new ScoreTrendDTO(
                        p.getDate().toLocalDate(),
                        p.getAvgScore() != null ? p.getAvgScore() : 0.0))
                .toList();
    }
}
