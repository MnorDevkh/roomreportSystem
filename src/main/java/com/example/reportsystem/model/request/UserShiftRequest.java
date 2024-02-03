package com.example.reportsystem.model.request;

import lombok.*;

import java.time.LocalDate;
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserShiftRequest {
    private long user;
    private long shift;
    private LocalDate date;
}
