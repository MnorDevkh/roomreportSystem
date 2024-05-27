package com.example.reportsystem.model;

import com.example.reportsystem.model.report.Room;
import com.example.reportsystem.model.toDto.LecturerDto;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Lecturer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lecturer_id")
    private long id;
    private String name;
    private String description;
    private LocalDate date;

//    @ManyToMany(fetch = FetchType.EAGER)
//    @JoinTable(
//            name = "lecturer_room",
//            joinColumns = @JoinColumn(name = "lecturer_id"),
//            inverseJoinColumns = @JoinColumn(name = "room_id")
//    )
//    private Set<Room> rooms = new HashSet<>();
//
//    @ManyToMany(fetch = FetchType.EAGER)
//    @JoinTable(
//            name = "lecturer_shift",
//            joinColumns = @JoinColumn(name = "lecturer_id"),
//            inverseJoinColumns = @JoinColumn(name = "shift_id")
//    )
//    private Set<Shift> shifts = new HashSet<>();
//
//    @ManyToMany(fetch = FetchType.EAGER)
//    @JoinTable(
//            name = "lecturer_subject",
//            joinColumns = @JoinColumn(name = "lecturer_id"),
//            inverseJoinColumns = @JoinColumn(name = "subject_id")
//    )
//    private Set<Subject> subjects = new HashSet<>();

    public LecturerDto toDto() {
        return new LecturerDto(this.id, this.name, this.description);
    }
}
