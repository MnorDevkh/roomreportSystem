package com.example.reportsystem.model.request;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RoomRequest {
    private String name;
    private String floor;
    private String type;
    private String description;
}
