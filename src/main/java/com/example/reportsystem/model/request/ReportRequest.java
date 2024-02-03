package com.example.reportsystem.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReportRequest {
    private List<Long> roomIds;
    private long userId;
    private long shiftId;
    private long subjectId;
    private LocalDate date;
    private Integer studentNum;
}
