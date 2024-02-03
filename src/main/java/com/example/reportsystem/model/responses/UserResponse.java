package com.example.reportsystem.model.responses;

import com.example.reportsystem.enums.Role;
import com.example.reportsystem.model.toDto.ShiftDto;
import com.example.reportsystem.model.toDto.SubjectDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class UserResponse {
    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private Role role;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<ShiftDto> shift;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<SubjectDto> subject;
}
