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
    @Column(name = "session_i_id")
    private Integer id;
    private String questionTitle;
    @OneToMany(mappedBy = "sessionI", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<QuestionList> questionList;
    @OneToMany(mappedBy = "sessionI", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AnswerList> answerList;
    @OneToOne(mappedBy = "sessionI")
    private Sessions sessions;
}
