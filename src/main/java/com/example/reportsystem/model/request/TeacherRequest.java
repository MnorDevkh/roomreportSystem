package com.example.reportsystem.model.request;

import lombok.*;

import java.time.LocalDate;
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TeacherRequest {
    private String name;
    private String description;
    private LocalDate date = LocalDate.now();
}
