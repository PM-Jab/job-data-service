package com.ata.job.service;

import com.ata.job.model.JobRequestParam;
import com.ata.job.model.JobResponseBody;
import com.ata.job.repository.JobRepository;
import com.ata.job.repository.JobSpecification;
import com.ata.job.repository.entity.Job;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JobService {

    private final JobRepository jobRepository;

    public List<JobResponseBody> getJobsFiltered(JobRequestParam req) {
        Specification<Job> spec = Specification.where(JobSpecification.fromParams(req));
        Sort sort = Sort.by(Sort.Direction.fromString(req.getSortType()), req.getSort() != null ? req.getSort() : "timestamp");

        Set<String> fields = null;
        if (req.getFields() != null && !req.getFields().isBlank()) {
            fields = Arrays.stream(req.getFields().split(","))
                    .map(String::trim)
                    .collect(Collectors.toSet());
        }

        final Set<String> requestedFields = fields;
        return jobRepository.findAll(spec, sort).stream()
                .map(job -> toResponse(job, requestedFields))
                .toList();
    }

    private JobResponseBody toResponse(Job job, Set<String> fields) {
        boolean all = fields == null;
        return JobResponseBody.builder()
                .timestamp(all || fields.contains("timestamp") ? job.getTimestamp() : null)
                .employer(all || fields.contains("employer") ? job.getEmployer() : null)
                .location(all || fields.contains("location") ? job.getLocation() : null)
                .jobTitle(all || fields.contains("job_title") ? job.getJobTitle() : null)
                .yearsAtEmployer(all || fields.contains("years_at_employer") ? job.getYearsAtEmployer() : null)
                .yearsOfExperience(all || fields.contains("years_of_experience") ? job.getYearsOfExperience() : null)
                .salary(all || fields.contains("salary") ? (job.getSalary() != null ? job.getSalary().toString() : null) : null)
                .signingBonus(all || fields.contains("signing_bonus") ? (job.getSigningBonus() != null ? job.getSigningBonus().toString() : null) : null)
                .annualBonus(all || fields.contains("annual_bonus") ? (job.getAnnualBonus() != null ? job.getAnnualBonus().toString() : null) : null)
                .annualStockValueBonus(all || fields.contains("annual_stock_value_bonus") ? (job.getAnnualStockValueBonus() != null ? job.getAnnualStockValueBonus().toString() : null) : null)
                .gender(all || fields.contains("gender") ? job.getGender() : null)
                .additionalComments(all || fields.contains("additional_comments") ? job.getAdditionalComments() : null)
                .build();
    }
}
