package com.example.reportsystem.model.question.request;

import com.example.reportsystem.model.question.AnswerList;
import com.example.reportsystem.model.question.QuestionList;
import com.example.reportsystem.model.question.SessionII;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SessionIIAQRequest {
    private QuestionListRequest question;
    private List<AnswerListRequest> answerList;
}
