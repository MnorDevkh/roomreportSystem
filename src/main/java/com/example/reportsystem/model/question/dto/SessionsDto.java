package com.example.reportsystem.model.question.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SessionsDto {
    private Integer id;
    private String question;
    private String answer;
}
