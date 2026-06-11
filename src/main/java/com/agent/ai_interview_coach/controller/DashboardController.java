package com.agent.ai_interview_coach.controller;

import com.agent.ai_interview_coach.dto.response.ApiResponse;
import com.agent.ai_interview_coach.dto.response.DashboardStatsDTO;
import com.agent.ai_interview_coach.dto.response.ScoreTrendDTO;
import com.agent.ai_interview_coach.service.InterviewSessionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final InterviewSessionService sessionService;

    /**
     * GET /api/dashboard/stats
     * Returns aggregate stats: totals, average score, best/weakest skill.
     */
    @GetMapping("/stats")
    public ResponseEntity<ApiResponse<DashboardStatsDTO>> getStats() {
        log.info("GET /api/dashboard/stats");
        DashboardStatsDTO stats = sessionService.getDashboardStats();
        return ResponseEntity.ok(ApiResponse.ok(stats));
    }

    /**
     * GET /api/dashboard/trend
     * Returns daily average scores sorted ascending by date.
     */
    @GetMapping("/trend")
    public ResponseEntity<ApiResponse<List<ScoreTrendDTO>>> getTrend() {
        log.info("GET /api/dashboard/trend");
        List<ScoreTrendDTO> trend = sessionService.getScoreTrend();
        return ResponseEntity.ok(ApiResponse.ok(trend));
    }
}
