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
public class QuestionMetadata extends BaseModel {

    private String functionName;

    private String returnType;

    @OneToMany(mappedBy = "questionMetadata", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ParamInfo> parameters = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private Language language;

    @Column(columnDefinition = "TEXT")
    private String codeTemplate;

    @Column(columnDefinition = "TEXT")
    private String testCaseFormat; // Optional: JSON string to define test case structure

    private String executionStrategy; // e.g. "function", "main-based", "class"

    private Boolean customInputEnabled;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    private Question question;

    @ElementCollection
    @CollectionTable(name = "question_metadata_custom_ds", joinColumns = @JoinColumn(name = "question_metadata_id"))
    @MapKeyColumn(name = "custom_key")
    @Column(name = "value")
    private Map<String, String> customDataStructureNames = new HashMap<>();
}

