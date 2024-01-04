package com.example.reportsystem.model;

import com.example.reportsystem.model.toDto.TeacherDto;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "teacher_id")
    private Integer id;
    private String name;
    private String description;
    private LocalDate date;

    public TeacherDto toDto() {
        return new TeacherDto(this.id,this.name, this.description,this.date);
    }
}
