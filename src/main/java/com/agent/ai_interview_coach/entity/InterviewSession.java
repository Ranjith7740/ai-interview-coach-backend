package com.agent.ai_interview_coach.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(
    name = "interview_sessions",
    indexes = {
        @Index(name = "idx_is_skill",      columnList = "skill"),
        @Index(name = "idx_is_created_at", columnList = "created_at"),
        @Index(name = "idx_is_score",      columnList = "score")
    }
)
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InterviewSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "skill", length = 100)
    private String skill;

    @Column(name = "question", columnDefinition = "TEXT", nullable = false)
    private String question;

    @Column(name = "answer", columnDefinition = "TEXT", nullable = false)
    private String answer;

    @Column(name = "score", nullable = false)
    private Integer score;

    @Column(name = "feedback", columnDefinition = "TEXT")
    private String feedback;

    @Column(name = "missing_concepts", columnDefinition = "TEXT")
    private String missingConcepts;

    @Column(name = "correct_answer", columnDefinition = "TEXT")
    private String correctAnswer;

    @Column(name = "improvement_suggestions", columnDefinition = "TEXT")
    private String improvementSuggestions;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    // user_id reserved for future multi-user support
}
