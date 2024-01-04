package com.example.reportsystem.model.responses;

import com.example.reportsystem.model.Room;
import com.example.reportsystem.model.Shift;
import com.example.reportsystem.model.Teacher;
import com.example.reportsystem.model.toDto.RoomDto;
import com.example.reportsystem.model.toDto.ShiftDto;
import com.example.reportsystem.model.toDto.TeacherDto;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Getter
@Setter
@Builder
public class ReportResponse {
    private Integer id;
    private RoomDto room;
    private TeacherDto teacher;
    private ShiftDto shift;
    private LocalDate date ;
    private LocalDate create;
    private Integer studentNum;
}
