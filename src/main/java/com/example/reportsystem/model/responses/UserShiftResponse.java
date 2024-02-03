package com.example.reportsystem.model.responses;

import com.example.reportsystem.model.toDto.ShiftDto;
import com.example.reportsystem.model.toDto.UserDto;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class UserShiftResponse {
    private long id;
    private UserDto user;
    private ShiftDto shift;
}
