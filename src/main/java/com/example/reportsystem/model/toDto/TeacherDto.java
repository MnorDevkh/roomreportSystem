package com.example.reportsystem.model.toDto;

import com.example.reportsystem.model.Teacher;
import jakarta.persistence.Entity;
import lombok.*;

import java.time.LocalDate;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TeacherDto{
    private Integer id;
    private String name;
    private String description;
    private LocalDate date = LocalDate.now();

}
