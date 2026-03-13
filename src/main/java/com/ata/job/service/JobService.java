package com.ata.job.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class JobService {
    public List<JobResponseBody> getJobsBySalary(JobRequestBody req) {}

    public List<JobResponseBody> getJobsByFields(JobRequestBody req) {}

    public List<JobResponseBody> getJobsSorted(JobRequestBody req) {}
}