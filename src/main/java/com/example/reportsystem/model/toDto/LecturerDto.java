package com.example.reportsystem.model.toDto;

import lombok.*;

import java.time.LocalDate;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LecturerDto {
    private long id;
    private String name;
    private String description;

}
