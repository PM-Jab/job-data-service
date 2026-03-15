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

    // GET /job_data?fields=job_title,gender,salary
    // GET /job_data?salary[gte]=100&fields=job_title
    // GET /job_data?salary[gte]=100&fields=job_title&sort=job_title&sort_type=DESC
    @GetMapping(params = "fields")
    public ResponseEntity<List<Map<String, Object>>> getJobsFilterdColumn(
            @RequestParam(value = "sort", required = false) String sort,
            @RequestParam(value = "sort_type", required = false, defaultValue = "ASC") String sortType,
            @RequestParam(value = "fields", required = false) String fields,

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
                .fields(fields)
                .minSalary(minSalary)
                .maxSalary(maxSalary)
                .jobTitle(jobTitle)
                .gender(gender)
                .sort(sort)
                .sortType(sortType)
                .build();

        return ResponseEntity.ok();
    }
}
