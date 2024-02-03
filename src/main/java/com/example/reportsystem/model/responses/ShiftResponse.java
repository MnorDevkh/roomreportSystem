package com.example.reportsystem.model.responses;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class ShiftResponse {
    private long id;
    private String name;
    private String description;
}
