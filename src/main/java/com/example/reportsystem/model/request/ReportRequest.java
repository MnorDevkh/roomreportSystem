package com.example.reportsystem.model.request;

import com.example.reportsystem.model.report.Room;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReportRequest {
    private Set<Integer> roomIds;
    private Integer shiftId;
    private Integer subjectId;
    private LocalDate date;
    private Integer studentNum;
}
