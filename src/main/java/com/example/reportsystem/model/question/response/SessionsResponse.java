package com.example.reportsystem.model.question.response;


import com.example.reportsystem.model.question.*;
import com.example.reportsystem.model.question.dto.AnswerListDto;
import com.example.reportsystem.model.question.dto.QuestionListDto;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SessionsResponse {
    private Integer id;
    private SessionI sessionI;
    private SessionII sessionII;
    private SessionIII sessionIII;
    private SessionIV sessionIV;
    private SessionV sessionV;
}
