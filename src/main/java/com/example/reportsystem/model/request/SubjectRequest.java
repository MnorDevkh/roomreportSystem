package com.example.reportsystem.model.request;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubjectRequest {
    private String name;
    private String description;
}
