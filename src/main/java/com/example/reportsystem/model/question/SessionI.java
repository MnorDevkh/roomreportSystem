package com.example.reportsystem.model.question;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SessionI {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sessions_id")
    private Integer id;
    private String questionTitle;
    @OneToMany(mappedBy = "sessions", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<QuestionList> questionList;
    @OneToMany(mappedBy = "sessions", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AnswerList> answerList;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    private Questions questions;
}
