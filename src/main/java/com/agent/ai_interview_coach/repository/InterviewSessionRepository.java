package com.agent.ai_interview_coach.repository;

import com.agent.ai_interview_coach.entity.InterviewSession;
import com.agent.ai_interview_coach.repository.projection.ScoreTrendProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InterviewSessionRepository extends JpaRepository<InterviewSession, Long> {

    // ── History ───────────────────────────────────────────────────────────────

    Page<InterviewSession> findAll(Pageable pageable);

    // ── Dashboard Stats ───────────────────────────────────────────────────────

    @Query("SELECT AVG(s.score) FROM InterviewSession s")
    Double findAverageScore();

    /**
     * Skills ordered by their average score descending.
     * Pageable(0,1) → best skill; Pageable(last,1) → weakest.
     */
    @Query("SELECT s.skill FROM InterviewSession s WHERE s.skill IS NOT NULL GROUP BY s.skill ORDER BY AVG(s.score) DESC")
    List<String> findSkillsOrderedByAvgScoreDesc(Pageable pageable);

    @Query("SELECT s.skill FROM InterviewSession s WHERE s.skill IS NOT NULL GROUP BY s.skill ORDER BY AVG(s.score) ASC")
    List<String> findSkillsOrderedByAvgScoreAsc(Pageable pageable);

    // ── Score Trend ───────────────────────────────────────────────────────────

    @Query(
        value = """
            SELECT DATE(created_at) AS date,
                   ROUND(AVG(score), 2) AS avgScore
            FROM interview_sessions
            GROUP BY DATE(created_at)
            ORDER BY date ASC
            """,
        nativeQuery = true
    )
    List<ScoreTrendProjection> findScoreTrend();
}
