package com.example.reportsystem.model.responses;

import lombok.*;

import java.time.LocalDate;

@Data
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoomResponse {
    private long id;
    private String name;
    private String floor;
    private String type;
    private String description;
    private LocalDate date = LocalDate.now();
}
