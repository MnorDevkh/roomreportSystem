package com.example.reportsystem.model;

import com.example.reportsystem.model.toDto.ShiftDto;
import com.example.reportsystem.model.toDto.SubjectDto;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subject_id")
    private Integer id;
    private String name;
    private String description;
    private LocalDate date;
    private LocalDate deleteAtDate;
    private boolean deleted;
    @ManyToMany(mappedBy = "subjects")
    Set<User> users;
    public SubjectDto toDto(){return new SubjectDto(this.id,this.name,this.description);}

}
