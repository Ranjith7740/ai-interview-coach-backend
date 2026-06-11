package com.agent.ai_interview_coach.constant;

public final class GeminiConstants {

    private GeminiConstants() {}

    // ── Endpoint ──────────────────────────────────────────────────────────────
    public static final String GENERATE_CONTENT_PATH =
            "/v1beta/models/{model}:generateContent";

    // ── Prompts ───────────────────────────────────────────────────────────────
    public static final String QUESTION_PROMPT_TEMPLATE =
            "You are a senior technical interviewer. Generate one medium-level interview question "
            + "for the skill: %s. Return only the question, nothing else.";

    public static final String EVALUATION_PROMPT_TEMPLATE =
            "You are a strict senior technical interviewer. Evaluate the following candidate answer.\n\n"
            + "Question: %s\n"
            + "Candidate Answer: %s\n\n"
            + "Return a JSON object in the following format ONLY (no markdown, no explanation):\n"
            + "{\n"
            + "  \"score\": <integer 0-10>,\n"
            + "  \"feedback\": \"<detailed feedback string>\",\n"
            + "  \"missingConcepts\": \"<key concepts the candidate missed>\",\n"
            + "  \"correctAnswer\": \"<what the ideal answer should include>\",\n"
            + "  \"improvementSuggestions\": \"<actionable suggestions for the candidate>\"\n"
            + "}";
}
