package com.example.reportsystem.model.question.request;

import lombok.*;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuestionsRequest {
    private String title;
    private String semester;
    private String year;
    private String subject;
    private String shift;
    private String lecture;
    private SessionsRequest sessionRequest;
}
