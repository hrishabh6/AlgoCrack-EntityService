package com.hrishabh.algocrackentityservice.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Tracks aggregate statistics for each question.
 * Used for displaying acceptance rates, average runtimes, etc.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "question_statistics", indexes = {
        @Index(name = "idx_stats_question_id", columnList = "questionId", unique = true)
})
public class QuestionStatistics extends BaseModel {

    /**
     * Question ID (denormalized for easier querying)
     */
    @Column(nullable = false, unique = true)
    private Long questionId;

    // ========== Submission Counts ==========

    /**
     * Total number of submissions for this question
     */
    @Column(nullable = false)
    @Builder.Default
    private Integer totalSubmissions = 0;

    /**
     * Number of accepted submissions
     */
    @Column(nullable = false)
    @Builder.Default
    private Integer acceptedSubmissions = 0;

    // ========== Performance Metrics ==========

    /**
     * Average runtime of accepted submissions (ms)
     */
    @Column
    private Integer avgRuntimeMs;

    /**
     * Average memory usage of accepted submissions (KB)
     */
    @Column
    private Integer avgMemoryKb;

    /**
     * Best (minimum) runtime achieved (ms)
     */
    @Column
    private Integer bestRuntimeMs;

    /**
     * Best (minimum) memory usage achieved (KB)
     */
    @Column
    private Integer bestMemoryKb;

    // ========== User Metrics ==========

    /**
     * Number of unique users who attempted this question
     */
    @Column
    private Integer uniqueAttempts;

    /**
     * Number of unique users who solved this question
     */
    @Column
    private Integer uniqueSolves;

    /**
     * Average number of attempts before solving
     */
    @Column
    private Double avgAttemptsToSolve;

    // ========== Timestamps ==========

    /**
     * Timestamp of the most recent submission
     */
    @Column
    private LocalDateTime lastSubmissionAt;

    // ========== Versioning ==========

    /**
     * Version for optimistic locking
     */
    @Version
    private Long version;

    // ========== Calculated Methods ==========

    /**
     * Calculate acceptance rate as percentage
     */
    public Double getAcceptanceRate() {
        if (totalSubmissions == null || totalSubmissions == 0) {
            return 0.0;
        }
        return (acceptedSubmissions * 100.0) / totalSubmissions;
    }

    /**
     * Increment submission count
     */
    public void incrementSubmissions(boolean accepted, Integer runtimeMs, Integer memoryKb) {
        this.totalSubmissions++;
        if (accepted) {
            this.acceptedSubmissions++;

            // Update best metrics
            if (runtimeMs != null) {
                if (this.bestRuntimeMs == null || runtimeMs < this.bestRuntimeMs) {
                    this.bestRuntimeMs = runtimeMs;
                }
            }
            if (memoryKb != null) {
                if (this.bestMemoryKb == null || memoryKb < this.bestMemoryKb) {
                    this.bestMemoryKb = memoryKb;
                }
            }
        }
        this.lastSubmissionAt = LocalDateTime.now();
    }
}
