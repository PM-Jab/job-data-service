package com.ata.job.repository;

import com.ata.job.model.JobRequestParam;
import com.ata.job.repository.entity.Job;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Selection;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class JobSpecification {

    public static Specification<Job> fromParams(JobRequestParam params) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (params.getMinSalary() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("salary"), params.getMinSalary()));
            }
            if (params.getMaxSalary() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("salary"), params.getMaxSalary()));
            }
            if (params.getJobTitle() != null && !params.getJobTitle().isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("jobTitle")),
                        "%" + params.getJobTitle().toLowerCase() + "%"));
            }
            if (params.getGender() != null && !params.getGender().isBlank()) {
                predicates.add(cb.equal(cb.lower(root.get("gender")),
                        params.getGender().toLowerCase()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
