package com.example.reportsystem.model;

import com.example.reportsystem.model.toDto.ShiftDto;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Set;

@Entity
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
    private LocalDate date;
    private LocalDate deleteAtDate;
    private boolean deleted = false;
    @ManyToMany(mappedBy = "shifts")
    Set<User> users;
    public ShiftDto toDto() {
        return new ShiftDto(this.id,this.name,this.description);
    }
}
