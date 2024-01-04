package com.example.reportsystem.model;

import com.example.reportsystem.model.toDto.RoomDto;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_id")
    private Integer id;
    private String name;
    private String floor;
    private String type;
    private String description;
    private LocalDate date = LocalDate.now();

    public RoomDto toDto() {
        return new RoomDto(this.id, this.name,this.floor,this.type,this.description,this.date);
    }
}
