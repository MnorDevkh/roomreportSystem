package com.example.reportsystem.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

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
    @Column(name = "id", nullable = false)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Room room;

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

    private LocalDate date;

    private LocalDate createdate;

    private Integer studentNum;
    private LocalDate deleteAtDate;
    private boolean status = false;
}
