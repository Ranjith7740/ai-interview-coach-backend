package com.agent.ai_interview_coach.service.impl;

import com.agent.ai_interview_coach.client.GeminiClient;
import com.agent.ai_interview_coach.constant.GeminiConstants;
import com.agent.ai_interview_coach.dto.request.EvaluationRequestDTO;
import com.agent.ai_interview_coach.dto.request.QuestionRequestDTO;
import com.agent.ai_interview_coach.dto.response.EvaluationResponseDTO;
import com.agent.ai_interview_coach.dto.response.QuestionResponseDTO;
import com.agent.ai_interview_coach.exception.ResponseParseException;
import com.agent.ai_interview_coach.service.InterviewService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class InterviewServiceImpl implements InterviewService {

    private final GeminiClient geminiClient;
    private final ObjectMapper objectMapper;

    @Override
    public QuestionResponseDTO generateQuestion(QuestionRequestDTO request) {
        log.info("Generating interview question for skill: {}", request.getSkill());

        String prompt = String.format(GeminiConstants.QUESTION_PROMPT_TEMPLATE, request.getSkill());
        String rawQuestion = geminiClient.generateContent(prompt).strip();

        log.info("Question generated successfully for skill: {}", request.getSkill());

        return QuestionResponseDTO.builder()
                .skill(request.getSkill())
                .question(rawQuestion)
                .build();
    }

    @Override
    public EvaluationResponseDTO evaluateAnswer(EvaluationRequestDTO request) {
        log.info("Evaluating answer for question: [{}...]", abbreviate(request.getQuestion(), 60));

        String prompt = String.format(GeminiConstants.EVALUATION_PROMPT_TEMPLATE,
                request.getQuestion(), request.getAnswer());

        String rawJson = geminiClient.generateContent(prompt).strip();

        // Gemini sometimes wraps its JSON in markdown code fences — strip them
        rawJson = stripMarkdownFences(rawJson);

        try {
            EvaluationResponseDTO result = objectMapper.readValue(rawJson, EvaluationResponseDTO.class);
            log.info("Evaluation complete, score={}", result.getScore());
            return result;
        } catch (JsonProcessingException ex) {
            log.error("Failed to parse Gemini evaluation JSON. Raw response: {}", rawJson, ex);
            throw new ResponseParseException(
                    "Could not parse evaluation response from Gemini. Raw response: " + rawJson, ex);
        }
    }

    private String stripMarkdownFences(String text) {
        if (text.startsWith("```")) {
            text = text.replaceFirst("```(?:json)?\\s*", "");
            int end = text.lastIndexOf("```");
            if (end != -1) {
                text = text.substring(0, end).strip();
            }
        }
        return text;
    }

    private String abbreviate(String text, int maxLen) {
        return text.length() <= maxLen ? text : text.substring(0, maxLen) + "...";
    }
}
