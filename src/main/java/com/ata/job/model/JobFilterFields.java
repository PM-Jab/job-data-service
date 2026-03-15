package com.ata.job.model;

public enum JobFilterFields {
    JOB_TITLE("job_title"),
    GENDER("gender"),
    SALARY("salary");

    private final String value;

    JobFilterFields(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}