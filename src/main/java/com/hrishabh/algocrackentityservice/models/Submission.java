package com.hrishabh.algocrackentityservice.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Submission extends BaseModel {

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;

    @Column(columnDefinition = "TEXT")
    private String code;

    private String language;

    private Boolean isPassed;

    private LocalDateTime timeOfSubmission;

    private String typeOfError; // Optional

    // getters and setters
}
