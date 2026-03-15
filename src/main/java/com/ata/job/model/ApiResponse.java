package com.ata.job.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ApiResponse<T> {
    private String statusCode;
    private String message;
    private int count;
    private List<T> data;
}
