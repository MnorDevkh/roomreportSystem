package com.example.reportsystem.model.toDto;

import lombok.*;

import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class SubjectDto {
    private long id;
    private String name;
    private String description;
}
