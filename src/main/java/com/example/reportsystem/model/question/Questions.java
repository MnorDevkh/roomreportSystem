package com.example.reportsystem.model.question;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Questions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_id")
    private Integer id;

    @Column(nullable = false, length = 255)
    private String title;

    @Column(nullable = false, length = 50)
    private String semester;

    @Column(nullable = false, length = 4)
    private String year;

    @Column(nullable = false, length = 100)
    private String subject;

    @Column(nullable = false, length = 50)
    private String shift;

    @Column(nullable = false, length = 50)
    private String lecture;

    @OneToOne(optional = true)
    @JoinColumn(name = "session_id")
    private Sessions sessions;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Questions question = (Questions) o;
        return id != null && id.equals(question.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", semester='" + semester + '\'' +
                ", year='" + year + '\'' +
                ", subject='" + subject + '\'' +
                ", shift='" + shift + '\'' +
                ", lecture='" + lecture + '\'' +
                ", sessions=" + sessions +
                '}';
    }
}
