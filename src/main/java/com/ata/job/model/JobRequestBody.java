package com.ata.job.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class JobRequestBody {
    // salary filter
    @RequestParam(value = "salary[gte]")
    private Double minSalary;

    @RequestParam(value = "salary[lte]")
    private Double maxSalary;

    // field selection
    private String fields;

    // sorting
    private String sort;
    private String sortType = "ASC";
}