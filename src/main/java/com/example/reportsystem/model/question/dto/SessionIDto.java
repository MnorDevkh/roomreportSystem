package com.example.reportsystem.model.question.dto;

import com.example.reportsystem.model.question.AnswerList;
import com.example.reportsystem.model.question.QuestionList;
import com.example.reportsystem.model.question.Questions;
import com.example.reportsystem.model.question.request.AnswerListRequest;
import com.example.reportsystem.model.question.request.QuestionListRequest;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SessionIDto {

    private String questionTitle;
    @OneToMany(mappedBy = "sessionI", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<QuestionListRequest> questionList;
    @OneToMany(mappedBy = "sessionI", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AnswerListRequest> answerList;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    private Questions questions;
}
