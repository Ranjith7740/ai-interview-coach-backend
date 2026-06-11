package com.agent.ai_interview_coach.config;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Configuration
@RequiredArgsConstructor
public class WebClientConfig {

    private final GeminiProperties geminiProperties;

    @Bean
    public WebClient geminiWebClient() {
        int timeoutMs = geminiProperties.getTimeoutSeconds() * 1000;

        HttpClient httpClient = HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, timeoutMs)
                .responseTimeout(Duration.ofSeconds(geminiProperties.getTimeoutSeconds()))
                .doOnConnected(conn -> conn
                        .addHandlerLast(new ReadTimeoutHandler(geminiProperties.getTimeoutSeconds(), TimeUnit.SECONDS))
                        .addHandlerLast(new WriteTimeoutHandler(geminiProperties.getTimeoutSeconds(), TimeUnit.SECONDS)));

        return WebClient.builder()
                .baseUrl(geminiProperties.getBaseUrl())
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();
    }
}
