package com.example.reportsystem.model.responses;

import com.example.reportsystem.model.toDto.ShiftDto;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Getter
@Setter
@Builder
public class LecturerResponse {
    private long id;
    private String name;
    private String description;
    private List<ShiftDto> shiftDtos;
//    private List<ShiftDto> shiftDtos;
    private LocalDate date;
}
