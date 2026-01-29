package com.hrishabh.algocrackentityservice.models;

import jakarta.persistence.*;
import lombok.*;

/**
 * Tracks detailed execution metrics for analytics and monitoring.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "execution_metrics", indexes = {
        @Index(name = "idx_metrics_submission_id", columnList = "submissionId")
})
public class ExecutionMetrics extends BaseModel {

    /**
     * Reference to the submission
     */
    @Column(nullable = false, length = 36)
    private String submissionId;

    // ========== Timing Breakdown ==========

    /**
     * Time waiting in queue (ms)
     */
    @Column
    private Integer queueWaitMs;

    /**
     * Compilation time (ms)
     */
    @Column
    private Integer compilationMs;

    /**
     * Total execution time (ms)
     */
    @Column
    private Integer executionMs;

    /**
     * End-to-end time (ms)
     */
    @Column
    private Integer totalMs;

    // ========== Resource Usage ==========

    /**
     * Peak memory usage (KB)
     */
    @Column
    private Integer peakMemoryKb;

    /**
     * CPU time used (ms)
     */
    @Column
    private Integer cpuTimeMs;

    // ========== System Info ==========

    /**
     * Worker that processed this submission
     */
    @Column(length = 50)
    private String workerId;

    /**
     * Hostname/IP of execution node
     */
    @Column(length = 100)
    private String executionNode;

    /**
     * Whether result was served from cache
     */
    @Column
    private Boolean usedCache;

    /**
     * Docker container ID (if applicable)
     */
    @Column(length = 64)
    private String containerId;

    /**
     * Test case timing breakdown as JSON
     * [{"index": 0, "compileMs": 450, "executeMs": 15}, ...]
     */
    @Column(columnDefinition = "JSON")
    private String testCaseTimings;
}
