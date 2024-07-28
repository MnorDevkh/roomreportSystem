package com.example.reportsystem.model.request;

import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequest {
    private List<Integer> subjectsId;
    private List<Integer> shiftId;
}
