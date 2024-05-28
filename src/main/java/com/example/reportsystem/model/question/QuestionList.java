package com.example.reportsystem.model.question;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuestionList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_list_id")
    private Integer id;
    private String question;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    private SessionI sessionI;

    @Override
    public String toString() {
        return "QuestionList{" +
                "id=" + id +
                ", question='" + question + '\'' +
//                ", sessionI=" + sessionI +
                '}';
    }
}
