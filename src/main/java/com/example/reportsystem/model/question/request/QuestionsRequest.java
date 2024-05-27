package com.example.reportsystem.model.question.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class QuestionsRequest {
    private String title;
    private String Semester;
    private String year;
    private String subject;
    private String shift;
    private String lecture;
    private List<SessionsRequest> sessions;
}
