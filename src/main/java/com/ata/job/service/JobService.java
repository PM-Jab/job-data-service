package com.ata.job.service;

import com.ata.job.model.JobRequestParam;
import com.ata.job.model.JobResponseBody;
import com.ata.job.repository.JobRepository;
import com.ata.job.repository.JobSpecification;
import com.ata.job.repository.entity.Job;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class JobService {

    private final JobRepository jobRepository;

    public List<JobResponseBody> getJobsFilteredRows(JobRequestParam req) {
        return jobRepository
                .findAll(JobSpecification.fromParams(req))
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public List<JobResponseBody> getJobsFilteredColumns(JobRequestParam req) {
        return
    }

    public List<JobResponseBody> getJobsSorted(JobRequestParam req) {
    }

    private JobResponseBody toResponse(Job job) {
        return JobResponseBody.builder()
                .timestamp(job.getTimestamp())
                .employer(job.getEmployer())
                .location(job.getLocation())
                .jobTitle(job.getJobTitle())
                .yearsAtEmployer(job.getYearsAtEmployer())
                .yearsOfExperience(job.getYearsOfExperience())
                .salary(job.getSalary() != null ? job.getSalary().toString() : null)
                .signingBonus(job.getSigningBonus() != null ? job.getSigningBonus().toString() : null)
                .annualBonus(job.getAnnualBonus() != null ? job.getAnnualBonus().toString() : null)
                .annualStockValueBonus(job.getAnnualStockValueBonus() != null ? job.getAnnualStockValueBonus().toString() : null)
                .gender(job.getGender())
                .additionalComments(job.getAdditionalComments())
                .build();
    }
}
