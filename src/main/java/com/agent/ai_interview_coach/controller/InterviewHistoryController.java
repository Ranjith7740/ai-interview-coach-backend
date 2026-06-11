package com.agent.ai_interview_coach.controller;

import com.agent.ai_interview_coach.dto.response.ApiResponse;
import com.agent.ai_interview_coach.dto.response.InterviewDetailDTO;
import com.agent.ai_interview_coach.dto.response.InterviewHistoryDTO;
import com.agent.ai_interview_coach.dto.response.PagedResponse;
import com.agent.ai_interview_coach.service.InterviewSessionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/interviews")
@RequiredArgsConstructor
public class InterviewHistoryController {

    private final InterviewSessionService sessionService;

    /**
     * GET /api/interviews?page=0&size=10&sort=createdAt,desc
     * Returns a paginated list of interview history summaries.
     */
    @GetMapping
    public ResponseEntity<ApiResponse<PagedResponse<InterviewHistoryDTO>>> getHistory(
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC)
            Pageable pageable) {

        log.info("GET /api/interviews — page={}, size={}", pageable.getPageNumber(), pageable.getPageSize());
        PagedResponse<InterviewHistoryDTO> data = sessionService.getHistory(pageable);
        return ResponseEntity.ok(ApiResponse.ok(data));
    }

    /**
     * GET /api/interviews/{id}
     * Returns the full detail of a single interview session.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<InterviewDetailDTO>> getById(@PathVariable Long id) {
        log.info("GET /api/interviews/{}", id);
        InterviewDetailDTO data = sessionService.getById(id);
        return ResponseEntity.ok(ApiResponse.ok(data));
    }
}
