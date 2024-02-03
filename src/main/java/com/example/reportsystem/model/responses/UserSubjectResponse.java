package com.example.reportsystem.model.responses;

import com.example.reportsystem.model.Subject;
import com.example.reportsystem.model.User;
import com.example.reportsystem.model.toDto.SubjectDto;
import com.example.reportsystem.model.toDto.UserDto;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class UserSubjectResponse {
    private long id;
    private UserDto user;
    private SubjectDto subject;
}
