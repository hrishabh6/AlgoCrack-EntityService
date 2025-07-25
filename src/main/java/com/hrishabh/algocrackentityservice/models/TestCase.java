package com.hrishabh.algocrackentityservice.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.Map;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TestCase extends BaseModel {

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;

    @Column(columnDefinition = "TEXT")
    private String input; // Store JSON as String

    @Column(columnDefinition = "TEXT")
    private String expectedOutput; // Store JSON as String

    private Integer orderIndex;

    private Boolean isHidden;

    // getters and setters
}

