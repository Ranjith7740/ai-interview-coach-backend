package com.agent.ai_interview_coach;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableConfigurationProperties
@EnableJpaAuditing   // required for @CreatedDate on InterviewSession.createdAt
public class AiInterviewCoachApplication {

	public static void main(String[] args) {
		SpringApplication.run(AiInterviewCoachApplication.class, args);
	}
}
