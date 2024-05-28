package com.example.reportsystem.model.question.response;

import com.example.reportsystem.model.question.AnswerList;
import com.example.reportsystem.model.question.QuestionList;
import com.example.reportsystem.model.question.Sessions;
import com.example.reportsystem.model.question.dto.AnswerListDto;
import com.example.reportsystem.model.question.dto.QuestionListDto;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SessionIResponse {

    private Integer id;
    private String questionTitle;
    private List<QuestionListDto> questionList;
    private List<AnswerListDto> answerList;
}
