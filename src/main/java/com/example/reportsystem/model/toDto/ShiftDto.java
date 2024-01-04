package com.example.reportsystem.model.toDto;

import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ShiftDto {
    private Integer id;
    private String name;
    private String description;
    private LocalDate date = LocalDate.now();

}
