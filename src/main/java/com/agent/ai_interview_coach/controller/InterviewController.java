package com.agent.ai_interview_coach.controller;

import com.agent.ai_interview_coach.dto.request.EvaluationRequestDTO;
import com.agent.ai_interview_coach.dto.request.QuestionRequestDTO;
import com.agent.ai_interview_coach.dto.response.EvaluationResponseDTO;
import com.agent.ai_interview_coach.dto.response.QuestionResponseDTO;
import com.agent.ai_interview_coach.service.InterviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/interview")
@RequiredArgsConstructor
public class InterviewController {

    private final InterviewService interviewService;

    /**
     * POST /api/interview/question
     * Generate a medium-level interview question for the given skill.
     */
    @PostMapping("/question")
    public ResponseEntity<QuestionResponseDTO> generateQuestion(
            @Valid @RequestBody QuestionRequestDTO request) {

        log.info("POST /api/interview/question — skill={}", request.getSkill());
        QuestionResponseDTO response = interviewService.generateQuestion(request);
        return ResponseEntity.ok(response);
    }

    /**
     * POST /api/interview/evaluate
     * Evaluate a candidate's answer and return a structured score + feedback.
     */
    @PostMapping("/evaluate")
    public ResponseEntity<EvaluationResponseDTO> evaluateAnswer(
            @Valid @RequestBody EvaluationRequestDTO request) {

        log.info("POST /api/interview/evaluate — question length={}", request.getQuestion().length());
        EvaluationResponseDTO response = interviewService.evaluateAnswer(request);
        return ResponseEntity.ok(response);
    }
}
