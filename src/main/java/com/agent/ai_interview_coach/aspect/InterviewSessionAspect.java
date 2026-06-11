package com.agent.ai_interview_coach.aspect;

import com.agent.ai_interview_coach.dto.request.EvaluationRequestDTO;
import com.agent.ai_interview_coach.dto.response.EvaluationResponseDTO;
import com.agent.ai_interview_coach.service.InterviewSessionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * Intercepts every successful call to {@code InterviewServiceImpl.evaluateAnswer}
 * and persists the result as an {@code InterviewSession}.
 *
 * Phase 1 code is untouched — this aspect is the only integration seam.
 *
 * Save failures are swallowed (logged as ERROR) so the evaluation response
 * already delivered to the client is never affected.
 */
@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class InterviewSessionAspect {

    private final InterviewSessionService sessionService;

    @AfterReturning(
        pointcut = "execution(* com.agent.ai_interview_coach.service.impl.InterviewServiceImpl.evaluateAnswer(..))",
        returning = "evaluationResult"
    )
    public void captureEvaluationResult(JoinPoint joinPoint, EvaluationResponseDTO evaluationResult) {
        try {
            EvaluationRequestDTO request = (EvaluationRequestDTO) joinPoint.getArgs()[0];
            sessionService.saveSession(request, evaluationResult);
            log.info("InterviewSessionAspect: session saved — skill={}, score={}",
                    request.getSkill(), evaluationResult.getScore());
        } catch (Exception ex) {
            // Best-effort persistence: never propagate to the caller
            log.error("InterviewSessionAspect: failed to save session — {}", ex.getMessage(), ex);
        }
    }
}
