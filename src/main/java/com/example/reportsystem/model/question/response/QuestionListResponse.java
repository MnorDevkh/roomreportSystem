package com.example.reportsystem.model.question.response;

import com.example.reportsystem.model.question.SessionI;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuestionListResponse {
    private Integer id;
    private String question;
}
