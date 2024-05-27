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
public class Sessions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sessions_id")
    private Integer id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "session_i_id")
    private SessionI sessionI;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "session_ii_id")
    private SessionII sessionII;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "session_iii_id")
    private SessionIII sessionIII;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "session_iv_id")
    private SessionIV sessionIV;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "session_v_id")
    private SessionV sessionV;
}
