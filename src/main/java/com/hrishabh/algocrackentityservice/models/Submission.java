package com.hrishabh.algocrackentityservice.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Represents a code submission made by a user for a specific question.
 * Tracks the entire lifecycle from queuing to completion with detailed metrics.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "submission", indexes = {
        @Index(name = "idx_submission_id", columnList = "submissionId"),
        @Index(name = "idx_user_status", columnList = "user_id, status"),
        @Index(name = "idx_question_status", columnList = "question_id, status"),
        @Index(name = "idx_status_queued", columnList = "status, queuedAt")
})
public class Submission extends BaseModel {

    // ========== External Reference ==========

    /**
     * UUID for external reference (used by frontend/APIs)
     */
    @Column(unique = true, nullable = false, length = 36)
    private String submissionId;

    // ========== Relationships ==========

    /**
     * User who submitted the code
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /**
     * Question being solved
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;

    // ========== Code Details ==========

    /**
     * Programming language (e.g., "java", "python", "cpp")
     */
    @Column(nullable = false, length = 20)
    private String language;

    /**
     * User's submitted source code
     */
    @Column(nullable = false, columnDefinition = "TEXT")
    private String code;

    // ========== Status Tracking ==========

    /**
     * Current processing status (QUEUED, COMPILING, RUNNING, COMPLETED, FAILED)
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private SubmissionStatus status;

    /**
     * Final verdict (ACCEPTED, WRONG_ANSWER, TLE, etc.)
     * Only set when status is COMPLETED
     */
    @Enumerated(EnumType.STRING)
    @Column(length = 30)
    private SubmissionVerdict verdict;

    // ========== Performance Metrics ==========

    /**
     * Execution runtime in milliseconds
     */
    @Column
    private Integer runtimeMs;

    /**
     * Memory usage in kilobytes
     */
    @Column
    private Integer memoryKb;

    // ========== Test Results ==========

    /**
     * JSON array of test case results
     * Format: [{"index": 0, "passed": true, "actualOutput": "[0,1]", "executionTimeMs": 15}, ...]
     */
    @Column(columnDefinition = "JSON")
    private String testResults;

    /**
     * Number of test cases passed
     */
    @Column
    private Integer passedTestCases;

    /**
     * Total number of test cases
     */
    @Column
    private Integer totalTestCases;

    // ========== Error Information ==========

    /**
     * Error message if execution failed
     */
    @Column(columnDefinition = "TEXT")
    private String errorMessage;

    /**
     * Compilation output/errors
     */
    @Column(columnDefinition = "TEXT")
    private String compilationOutput;

    // ========== Timestamps ==========

    /**
     * When the submission was queued for processing
     */
    @Column(nullable = false)
    private LocalDateTime queuedAt;

    /**
     * When execution started (left queue)
     */
    @Column
    private LocalDateTime startedAt;

    /**
     * When execution completed
     */
    @Column
    private LocalDateTime completedAt;

    // ========== Client Metadata ==========

    /**
     * Client IP address (IPv4: 15 chars, IPv6: 45 chars)
     */
    @Column(length = 45)
    private String ipAddress;

    /**
     * Client user agent string
     */
    @Column(columnDefinition = "TEXT")
    private String userAgent;

    // ========== Worker Info ==========

    /**
     * ID of the worker that processed this submission
     */
    @Column(length = 50)
    private String workerId;

    // ========== Calculated Methods ==========

    /**
     * Calculate total processing time from queue to completion
     */
    public Long getProcessingTimeMs() {
        if (queuedAt != null && completedAt != null) {
            return java.time.Duration.between(queuedAt, completedAt).toMillis();
        }
        return null;
    }

    /**
     * Calculate time spent waiting in queue
     */
    public Long getQueueWaitTimeMs() {
        if (queuedAt != null && startedAt != null) {
            return java.time.Duration.between(queuedAt, startedAt).toMillis();
        }
        return null;
    }

    /**
     * Check if submission was successful
     */
    public boolean isAccepted() {
        return verdict == SubmissionVerdict.ACCEPTED;
    }
}
