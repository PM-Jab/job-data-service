package com.ata.job.controller;

import com.ata.job.constant.JobConstants;
import com.ata.job.model.JobRequestParam;
import com.ata.job.model.JobResponseBody;
import com.ata.job.service.JobService;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
public class JobController {

    private final JobService jobService;

    // GET /job_data?fields=job_title,gender,salary
    // GET /job_data?salary[gte]=100&fields=job_title
    // GET /job_data?salary[gte]=100&fields=job_title&sort=job_title&sort_type=DESC
    @GetMapping(value="/job_data")
    public ResponseEntity<List<JobResponseBody>> getJobs(
            @RequestParam(value = "sort", required = false) String sort,
            @RequestParam(value = "sort_type", required = false, defaultValue = "ASC") String sortType,
            @RequestParam(value = "fields", required = false) String fields,

            @RequestParam(value = JobConstants.PARAM_SALARY_GTE, required = false)
            @DecimalMin(value = JobConstants.SALARY_MIN, message = JobConstants.MSG_SALARY_GTE_MIN)
            @DecimalMax(value = JobConstants.SALARY_MAX, message = JobConstants.MSG_SALARY_GTE_MAX)
            Double minSalary,

            @RequestParam(value = JobConstants.PARAM_SALARY_LTE, required = false)
            @DecimalMin(value = JobConstants.SALARY_MIN, message = JobConstants.MSG_SALARY_LTE_MIN)
            @DecimalMax(value = JobConstants.SALARY_MAX, message = JobConstants.MSG_SALARY_LTE_MAX)
            Double maxSalary,

            @RequestParam(value = JobConstants.PARAM_JOB_TITLE, required = false)
            @Size(max = JobConstants.JOB_TITLE_MAX_LENGTH, message = JobConstants.MSG_JOB_TITLE_SIZE)
            String jobTitle,

            @RequestParam(value = JobConstants.PARAM_GENDER, required = false)
            @Pattern(regexp = JobConstants.GENDER_PATTERN, flags = Pattern.Flag.CASE_INSENSITIVE,
                    message = JobConstants.MSG_GENDER_PATTERN)
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
        
        List<JobResponseBody> response = jobService.getJobsFiltered(req);
        return ResponseEntity.ok(response);
    }
}
