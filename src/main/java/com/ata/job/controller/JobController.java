package com.ata.job.controller;

import com.ata.job.constant.JobFilterConstants;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Validated
@RestController
@RequestMapping("/job_data")
public class JobController {

    // GET /job_data?salary[gte]=50000&salary[lte]=100000
    @GetMapping
    public ResponseEntity<List<Map<String, Object>>> getJobsByFilters(
            @RequestParam(value = JobFilterConstants.PARAM_SALARY_GTE, required = false)
            @DecimalMin(value = JobFilterConstants.SALARY_MIN, message = JobFilterConstants.MSG_SALARY_GTE_MIN)
            @DecimalMax(value = JobFilterConstants.SALARY_MAX, message = JobFilterConstants.MSG_SALARY_GTE_MAX)
            Double minSalary,

            @RequestParam(value = JobFilterConstants.PARAM_SALARY_LTE, required = false)
            @DecimalMin(value = JobFilterConstants.SALARY_MIN, message = JobFilterConstants.MSG_SALARY_LTE_MIN)
            @DecimalMax(value = JobFilterConstants.SALARY_MAX, message = JobFilterConstants.MSG_SALARY_LTE_MAX)
            Double maxSalary,

            @RequestParam(value = JobFilterConstants.PARAM_JOB_TITLE, required = false)
            @Size(max = JobFilterConstants.JOB_TITLE_MAX_LENGTH, message = JobFilterConstants.MSG_JOB_TITLE_SIZE)
            String jobTitle,

            @RequestParam(value = JobFilterConstants.PARAM_GENDER, required = false)
            @Pattern(regexp = JobFilterConstants.GENDER_PATTERN, flags = Pattern.Flag.CASE_INSENSITIVE,
                    message = JobFilterConstants.MSG_GENDER_PATTERN)
            String gender) {

        return ResponseEntity.ok();
    }


    // GET /job_data?fields=job_title,gender,salary
    @GetMapping(params = "fields")
    public ResponseEntity<List<Map<String, Object>>> getJobsByFields(
            @RequestParam("fields") String fields) {

        List<String> selectedFields = List.of(fields.split(","));


        return ResponseEntity.ok();
    }

    // GET /job_data?sort=job_title&sort_type=DESC
    @GetMapping(params = "sort")
    public ResponseEntity<List<Map<String, Object>>> getJobsSorted(
            @RequestParam("sort") String sortField,
            @RequestParam(value = "sort_type", defaultValue = "ASC") String sortType) {

        return ResponseEntity.ok();
    }
}
