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
public class SessionIIAQ {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "session_IIAQ_id")
    private Integer id;
    @OneToOne
    @JoinColumn(name = "question_List_id")
    private QuestionList questionList;
    @OneToMany(mappedBy = "sessionIIAQ", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AnswerList> answerList;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "session_id")
    private SessionII sessionII;

}
