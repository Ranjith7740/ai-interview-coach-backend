package com.agent.ai_interview_coach.dto.gemini;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

/**
 * Maps to the Gemini generateContent response body.
 * Only fields needed for text extraction are mapped; the rest are ignored.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class GeminiResponse {

    private List<Candidate> candidates;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Candidate {
        private Content content;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Content {
        private List<Part> parts;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Part {
        private String text;
    }

    /**
     * Extracts the generated text from the first candidate's first part.
     */
    public String extractText() {
        if (candidates == null || candidates.isEmpty()) return "";
        Candidate first = candidates.get(0);
        if (first.getContent() == null) return "";
        List<Part> parts = first.getContent().getParts();
        if (parts == null || parts.isEmpty()) return "";
        return parts.get(0).getText();
    }
}
