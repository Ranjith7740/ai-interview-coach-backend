package com.agent.ai_interview_coach.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "gemini.api")
public class GeminiProperties {

    private String key;
    private String baseUrl;
    private String model;
    private int timeoutSeconds;
    private Retry retry = new Retry();

    @Data
    public static class Retry {
        private int maxAttempts = 3;
        private int backoffSeconds = 2;
    }
}
