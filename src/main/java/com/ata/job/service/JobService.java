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
        Specification<Job> spec = Specification.where(JobSpecification.fromParams(req));
        if (req.getFields() != null && !req.getFields().isBlank()) {
            spec = spec.and(JobSpecification.forFields(List.of(req.getFields().split(","))));
        }
        Sort sort = Sort.by(Sort.Direction.fromString(req.getSortType()), req.getSort() != null ? req.getSort() : "timestamp");

        try {
            List<Job> jobs = jobRepository.findAll(spec, sort);
            return jobs.stream().map(this::toResponse).toList();
        } catch (Exception e) {
            log.error("Error fetching jobs with filters: {}", req, e);
            throw e;
        }
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
