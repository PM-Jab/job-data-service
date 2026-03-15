package com.ata.job.service;

import com.ata.job.model.JobRequestParam;
import com.ata.job.model.JobRequestBody;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class JobService {
    public List<JobResponseBody> getJobsBySalary(JobRequestParam req) {
        return List.of();
    }

    public List<JobResponseBody> getJobsByFields(JobRequestParam req) {
        return List.of();
    }

    public List<JobResponseBody> getJobsSorted(JobRequestParam req) {
        return List.of();
    }
}