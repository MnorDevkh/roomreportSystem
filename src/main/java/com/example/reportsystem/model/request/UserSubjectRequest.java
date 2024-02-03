package com.example.reportsystem.model.request;
import com.example.reportsystem.model.Shift;
import com.example.reportsystem.model.Subject;
import com.example.reportsystem.model.User;
import lombok.*;

import java.time.LocalDate;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserSubjectRequest {
    private long user;
    private long subject;
    private LocalDate date;
}
