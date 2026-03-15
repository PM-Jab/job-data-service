package com.ata.job.controller;

import com.ata.job.constant.JobParamValidateConstants;
import com.ata.job.model.JobRequestParam;
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
    public ResponseEntity<List<Map<String, Object>>> getJobsFilteredRow(
            @RequestParam(value = JobParamValidateConstants.PARAM_SALARY_GTE, required = false)
            @DecimalMin(value = JobParamValidateConstants.SALARY_MIN, message = JobParamValidateConstants.MSG_SALARY_GTE_MIN)
            @DecimalMax(value = JobParamValidateConstants.SALARY_MAX, message = JobParamValidateConstants.MSG_SALARY_GTE_MAX)
            Double minSalary,

            @RequestParam(value = JobParamValidateConstants.PARAM_SALARY_LTE, required = false)
            @DecimalMin(value = JobParamValidateConstants.SALARY_MIN, message = JobParamValidateConstants.MSG_SALARY_LTE_MIN)
            @DecimalMax(value = JobParamValidateConstants.SALARY_MAX, message = JobParamValidateConstants.MSG_SALARY_LTE_MAX)
            Double maxSalary,

            @RequestParam(value = JobParamValidateConstants.PARAM_JOB_TITLE, required = false)
            @Size(max = JobParamValidateConstants.JOB_TITLE_MAX_LENGTH, message = JobParamValidateConstants.MSG_JOB_TITLE_SIZE)
            String jobTitle,

            @RequestParam(value = JobParamValidateConstants.PARAM_GENDER, required = false)
            @Pattern(regexp = JobParamValidateConstants.GENDER_PATTERN, flags = Pattern.Flag.CASE_INSENSITIVE,
                    message = JobParamValidateConstants.MSG_GENDER_PATTERN)
            String gender) {

        JobRequestParam req = JobRequestParam.builder()
                .minSalary(minSalary)
                .maxSalary(maxSalary)
                .jobTitle(jobTitle)
                .gender(gender)
                .build();

        return ResponseEntity.ok();
    }


    // GET /job_data?fields=job_title,gender,salary
    @GetMapping(params = "fields")
    public ResponseEntity<List<Map<String, Object>>> getJobsFilterdColumn(
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
