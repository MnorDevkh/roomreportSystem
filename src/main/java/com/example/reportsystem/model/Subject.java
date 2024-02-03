package com.example.reportsystem.model;

import com.example.reportsystem.model.toDto.ShiftDto;
import com.example.reportsystem.model.toDto.SubjectDto;
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
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subject_id")
    private long id;
    private String name;
    private String description;
    private LocalDate date;
    private LocalDate deleteAtDate;
    private boolean status;
    public SubjectDto toDto(){return new SubjectDto(this.id,this.name,this.description);}

}
