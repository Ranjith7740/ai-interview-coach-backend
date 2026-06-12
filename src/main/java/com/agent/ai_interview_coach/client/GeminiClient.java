package com.agent.ai_interview_coach.client;

import com.agent.ai_interview_coach.config.GeminiProperties;
import com.agent.ai_interview_coach.constant.GeminiConstants;
import com.agent.ai_interview_coach.dto.gemini.GeminiRequest;
import com.agent.ai_interview_coach.dto.gemini.GeminiResponse;
import com.agent.ai_interview_coach.exception.GeminiApiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.util.retry.Retry;

import java.time.Duration;

@Slf4j
@Component
@RequiredArgsConstructor
public class GeminiClient {

    private final WebClient geminiWebClient;
    private final GeminiProperties geminiProperties;

    /**
     * Sends a prompt to the Gemini generateContent endpoint and returns
     * the raw generated text.  Retries on transient 5xx errors.
     */
public String generateContent(String prompt) {
    log.info("Sending prompt to Gemini model [{}], prompt length={}", geminiProperties.getModel(), prompt.length());

    GeminiRequest requestBody = GeminiRequest.of(prompt);

    try {
        GeminiResponse response = geminiWebClient.post()
                // Directly pass the fully interpolated base-url from your properties file!
                .uri(geminiProperties.getBaseUrl()) 
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(GeminiResponse.class)
                .retryWhen(Retry.backoff(
                        geminiProperties.getRetry().getMaxAttempts(),
                        Duration.ofSeconds(geminiProperties.getRetry().getBackoffSeconds()))
                    .filter(this::isRetryable)
                    .onRetryExhaustedThrow((spec, signal) ->
                            new GeminiApiException("Gemini API unreachable after retries: " + signal.failure().getMessage())))
                .block();

        if (response == null) {
            throw new GeminiApiException("Gemini returned an empty response");
        }

        String text = response.extractText();
        log.info("Gemini responded successfully, response length={}", text.length());
        return text;

    } catch (WebClientResponseException ex) {
        // ... rest of your catch blocks remain perfectly unchanged
            log.error("Gemini HTTP error: status={}, body={}", ex.getStatusCode(), ex.getResponseBodyAsString());
            throw new GeminiApiException(
                    String.format("Gemini API error [%s]: %s", ex.getStatusCode(), ex.getResponseBodyAsString()), ex);
        } catch (GeminiApiException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Unexpected error calling Gemini API: {}", ex.getMessage(), ex);
            throw new GeminiApiException("Unexpected error communicating with Gemini: " + ex.getMessage(), ex);
        }
    }

    private boolean isRetryable(Throwable throwable) {
        if (throwable instanceof WebClientResponseException ex) {
            int code = ex.getStatusCode().value();
            return code == 429 || code >= 500;
        }
        return false;
    }
}
