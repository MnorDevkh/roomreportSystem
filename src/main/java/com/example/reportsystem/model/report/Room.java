package com.example.reportsystem.model.report;

import com.example.reportsystem.model.Lecturer;
import com.example.reportsystem.model.User;
import com.example.reportsystem.model.toDto.RoomDto;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Entity
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
    private LocalDate deleteAtDate;
    private boolean status = false;
    @ManyToMany(mappedBy = "rooms")
    Set<Report> reports;
    public RoomDto toDto() {
        return new RoomDto(this.id, this.name,this.floor,this.type,this.description);
    }
}
