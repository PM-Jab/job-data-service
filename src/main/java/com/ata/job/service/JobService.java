package com.ata.job.service;

import com.ata.job.model.JobRequestParam;
import com.ata.job.model.JobResponseBody;
import com.ata.job.repository.JobRepository;
import com.ata.job.repository.JobSpecification;
import com.ata.job.repository.entity.Job;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class JobService {

    private final JobRepository jobRepository;

    public List<JobResponseBody> getJobsFiltered(JobRequestParam req) {
        return jobRepository
                .findAll(Specification.where(JobSpecification.filterByMinSalary(req.getMinSalary()))
                        .and(JobSpecification.filterByMaxSalary(req.getMaxSalary()))
                        .and(JobSpecification.filterByJobTitle(req.getJobTitle()))
                        .and(JobSpecification.filterByGender(req.getGender())),
                        Sort.by(Sort.Direction.fromString(req.getSortType()), req.getSort() != null ? req.getSort() : "timestamp"))
                .stream()
                .map(this::toResponse)
                .toList();
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
