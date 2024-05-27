package com.example.reportsystem.model.question;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SessionII {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "session_id")
    private Integer id;
    private String questionTitle;
    @OneToMany(mappedBy = "sessionII", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SessionIIAQ> sessionIIAQS;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    private Questions questions;
}
