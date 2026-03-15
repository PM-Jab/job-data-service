package com.ata.job.constant;

public class JobConstants {

    // param names
    public static final String PARAM_SALARY_GTE = "salary[gte]";
    public static final String PARAM_SALARY_LTE = "salary[lte]";
    public static final String PARAM_JOB_TITLE = "job_title";
    public static final String PARAM_GENDER = "gender";

    // salary constraints
    public static final String SALARY_MIN = "0";
    public static final String SALARY_MAX = "99999999";

    // salary validation messages
    public static final String MSG_SALARY_GTE_MIN = "salary[gte] must be >= 0";
    public static final String MSG_SALARY_GTE_MAX = "salary[gte] must be <= 99999999";
    public static final String MSG_SALARY_LTE_MIN = "salary[lte] must be >= 0";
    public static final String MSG_SALARY_LTE_MAX = "salary[lte] must be <= 99999999";

    // job_title constraints
    public static final int JOB_TITLE_MAX_LENGTH = 40;
    public static final String MSG_JOB_TITLE_SIZE = "job_title must be 40 characters or fewer";

    // gender constraints
    public static final String GENDER_PATTERN = "Male|Female";
    public static final String MSG_GENDER_PATTERN = "gender must be one of: male, female";
}
