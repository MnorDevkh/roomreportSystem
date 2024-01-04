package com.example.reportsystem.model.toDto;

import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RoomDto {
    private Integer id;
    private String name;
    private String floor;
    private String type;
    private String description;
    private LocalDate date = LocalDate.now();

}
