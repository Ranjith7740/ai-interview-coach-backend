package com.agent.ai_interview_coach.dto.gemini;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * Maps to the Gemini generateContent request body.
 * https://ai.google.dev/api/generate-content#request-body
 */
@Data
@Builder
public class GeminiRequest {

    private List<Content> contents;

    @Data
    @Builder
    public static class Content {
        private List<Part> parts;
    }

    @Data
    @Builder
    public static class Part {
        private String text;
    }

    public static GeminiRequest of(String prompt) {
        return GeminiRequest.builder()
                .contents(List.of(
                        Content.builder()
                                .parts(List.of(Part.builder().text(prompt).build()))
                                .build()
                ))
                .build();
    }
}
