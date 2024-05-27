package com.example.reportsystem.model.question.response;


import com.example.reportsystem.model.question.dto.AnswerListDto;
import com.example.reportsystem.model.question.dto.QuestionListDto;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SessionsResponse {

    private Integer id;
    private String questionTitle;
    private List<QuestionListDto> questionList;
    private List<AnswerListDto> answerList;
}
