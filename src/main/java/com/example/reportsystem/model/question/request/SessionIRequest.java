package com.example.reportsystem.model.question.request;

import com.example.reportsystem.model.question.AnswerList;
import com.example.reportsystem.model.question.QuestionList;
import com.example.reportsystem.model.question.Questions;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SessionIRequest {
    private String questionTitle;
    private List<QuestionListRequest> questionList;
    private List<AnswerListRequest> answerList;
}
