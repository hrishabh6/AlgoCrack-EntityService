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
public class User extends BaseModel {

    private String name;

    private String password;

    private String email;


    @Column(unique = true, nullable = false)
    private String userId;

    @ManyToMany
    @JoinTable(
            name = "user_questionsolved",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "question_id")
    )
    private Set<Question> questionsSolved = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Submission> pastSubmissions = new ArrayList<>();

    private String imgUrl; // Optional

    @Column(name = "ranking")
    private String rank;

    private Integer achievementPoints;


}
