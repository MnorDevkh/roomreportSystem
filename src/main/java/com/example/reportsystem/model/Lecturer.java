package com.example.reportsystem.model;

import com.example.reportsystem.model.toDto.LecturerDto;
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
public class Lecturer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lecturer_id")
    private long id;
    private String name;
    private String description;
    private LocalDate date;
    public LecturerDto toDto() {
        return new LecturerDto(this.id,this.name, this.description);
    }
}
