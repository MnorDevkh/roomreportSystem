package com.example.reportsystem.model;

import com.example.reportsystem.model.toDto.ShiftDto;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class Shift {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shift_id")
    private Integer id;
    private String name;
    private String description;
    private LocalDate date = LocalDate.now();

    public ShiftDto toDto() {
        return new ShiftDto(this.id,this.name,this.description,this.date);
    }
}
