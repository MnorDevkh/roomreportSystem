package com.example.reportsystem.model.toDto;

import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class RoomDto {
    private long id;
    private String name;
    private String floor;
    private String type;
    private String description;

}
