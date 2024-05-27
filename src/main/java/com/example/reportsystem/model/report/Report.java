package com.example.reportsystem.model.report;

import com.example.reportsystem.model.Shift;
import com.example.reportsystem.model.Subject;
import com.example.reportsystem.model.User;
import com.example.reportsystem.model.report.Room;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "reports")
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Builder
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "report_id", nullable = false)
    private Integer id;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "report_room",
            joinColumns = @JoinColumn(name = "report_id"),
            inverseJoinColumns = @JoinColumn(name = "room_id")
    )
    private Set<Room> rooms = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @NonNull
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shift_id")
    @NonNull
    private Shift shift;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id")
    @NonNull
    private Subject subject;

    private LocalDate reportDate;

    private LocalDate createDate;
    private LocalDate modifyDate;

    private Integer studentNum;
    private LocalDate deleteAtDate;
    private boolean status = false;
    private boolean deleted;
}
