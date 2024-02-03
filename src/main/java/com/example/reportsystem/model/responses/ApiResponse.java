package com.example.reportsystem.model.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiResponse<T>{

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T payload;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer page;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer size;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long totalElement;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer totalPages;
}


