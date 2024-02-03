package com.example.reportsystem.model.request;

import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class ShiftRequest {
    private String name;
    private String description;


}
