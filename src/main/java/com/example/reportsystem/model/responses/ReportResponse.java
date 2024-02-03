package com.example.reportsystem.model.responses;

import com.example.reportsystem.model.toDto.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Getter
@Setter
@Builder
public class ReportResponse {
    private long id;
    private List<RoomDto> room;
    private UserDto user;
    private ShiftDto shift;
    private SubjectDto subject;
    private Integer studentNum;
    private LocalDate date ;
    private LocalDate createDate;
}
