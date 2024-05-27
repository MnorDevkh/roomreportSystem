package com.example.reportsystem.model.question.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SessionsRequest {
    private String questionTitle;
    private List<QuestionListRequest> questionList;
    private List<AnswerListRequest> answerListRequests;
}
