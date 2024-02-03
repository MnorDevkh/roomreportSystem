package com.example.reportsystem.model.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PageResponse<T> {
    private T data;
    private Integer page;
    private Integer size;
    private long totalElement;
    private Integer totalPages;

}
