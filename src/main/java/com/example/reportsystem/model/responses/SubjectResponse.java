package com.example.reportsystem.model.responses;

import lombok.*;

import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class SubjectResponse {
    private long id;
    private String name;
    private String description;
    private LocalDate date;
}
