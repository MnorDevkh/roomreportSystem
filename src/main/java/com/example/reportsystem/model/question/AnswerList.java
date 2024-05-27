package com.example.reportsystem.model.question;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnswerList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "answer_list_id")
    private Integer id;
    private String answer;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "session_i_id")
    private SessionI sessionI;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "session_IIAQ_id")
    private SessionIIAQ sessionIIAQ;
}
