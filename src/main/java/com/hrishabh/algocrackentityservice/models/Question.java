package com.hrishabh.algocrackentityservice.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Question extends BaseModel {

    private String questionTitle;

    @Column(columnDefinition = "TEXT")
    private String questionDescription;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    private List<TestCase> testCases = new ArrayList<>();

    private Boolean isOutputOrderMatters;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    private List<Submission> correctAnswer = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "question_tag",
            joinColumns = @JoinColumn(name = "question_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private List<Tag> tags = new ArrayList<>();

    private String difficultyLevel;

    private String company; // Optional

    private String constraints;

    private Integer timeoutLimit;

}
