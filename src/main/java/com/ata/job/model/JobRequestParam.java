package com.ata.job.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JobRequestParam {
    // row filters
    private Double minSalary;
    private Double maxSalary;
    private String jobTitle;
    private String gender;

    // column filter = job_title,gender,salary
    private String fields;

    // sorting
    private String sort;
    private String sortType = "ASC";
}