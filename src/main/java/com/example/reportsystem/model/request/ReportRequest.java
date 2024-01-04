package com.example.reportsystem.model.request;

import com.example.reportsystem.model.Room;
import com.example.reportsystem.model.Shift;
import com.example.reportsystem.model.Teacher;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReportRequest {
    private Integer roomId;
    private Integer teacherId;
    private Integer shiftId;
    private LocalDate date;
    private Integer studentNum;
}
