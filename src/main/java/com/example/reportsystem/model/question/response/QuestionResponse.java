package com.example.reportsystem.model.question.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuestionResponse {
    private Integer id;
    private String title;
    private String semester;
    private String year;
    private String subject;
    private String shift;
    private String lecture;
    private List<SessionsResponse> sessions;
}
