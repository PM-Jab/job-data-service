package com.ata.job.controller;

import com.ata.job.model.ApiResponse;
import com.ata.job.model.JobRequestParam;
import com.ata.job.model.JobResponseBody;
import com.ata.job.service.JobService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JobControllerTest {

    @Mock
    private JobService jobService;

    @InjectMocks
    private JobController jobController;

    private JobResponseBody mockJob;

    @BeforeEach
    void setUp() {
        mockJob = JobResponseBody.builder()
                .jobTitle("Software Engineer")
                .gender("Male")
                .salary("120000.0")
                .build();
    }

    // Happy cases

    @Test
    void getJobs_withFieldsOnly_returns200WithBody() {
        when(jobService.getJobsFiltered(any())).thenReturn(List.of(mockJob));

        ResponseEntity<ApiResponse<JobResponseBody>> response = jobController.getJobs(
                null, "ASC", "job_title",
                null, null, null, null);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getStatusCode()).isEqualTo("200");
        assertThat(response.getBody().getMessage()).isEqualTo("Success");
        assertThat(response.getBody().getCount()).isEqualTo(1);
        assertThat(response.getBody().getData().get(0).getJobTitle()).isEqualTo("Software Engineer");
    }

    @Test
    void getJobs_withAllParams_passesCorrectReqToService() {
        when(jobService.getJobsFiltered(any())).thenReturn(List.of(mockJob));

        jobController.getJobs(
                "salary", "DESC", "job_title,gender,salary",
                50000.0, 150000.0, "Engineer", "Male");

        verify(jobService).getJobsFiltered(JobRequestParam.builder()
                .sort("salary")
                .sortType("DESC")
                .fields("job_title,gender,salary")
                .minSalary(50000.0)
                .maxSalary(150000.0)
                .jobTitle("Engineer")
                .gender("Male")
                .build());
    }

    @Test
    void getJobs_withNoFilters_returns200EmptyList() {
        when(jobService.getJobsFiltered(any())).thenReturn(List.of());

        ResponseEntity<ApiResponse<JobResponseBody>> response = jobController.getJobs(
                null, "ASC", "salary",
                null, null, null, null);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getStatusCode()).isEqualTo("200");
        assertThat(response.getBody().getMessage()).isEqualTo("Success");
        assertThat(response.getBody().getData()).isEmpty();
        assertThat(response.getBody().getCount()).isZero();
    }

    @Test
    void getJobs_withSortParams_passesCorrectSortToService() {
        when(jobService.getJobsFiltered(any())).thenReturn(List.of());

        jobController.getJobs(
                "salary", "DESC", "salary",
                null, null, null, null);

        verify(jobService).getJobsFiltered(JobRequestParam.builder()
                .sort("salary")
                .sortType("DESC")
                .fields("salary")
                .build());
    }

    @Test
    void getJobs_returnsExactListFromService() {
        JobResponseBody job1 = JobResponseBody.builder().jobTitle("Dev").build();
        JobResponseBody job2 = JobResponseBody.builder().jobTitle("Lead").build();
        when(jobService.getJobsFiltered(any())).thenReturn(List.of(job1, job2));

        ResponseEntity<ApiResponse<JobResponseBody>> response = jobController.getJobs(
                null, "ASC", "job_title",
                null, null, null, null);

        assertThat(response.getBody().getData()).containsExactly(job1, job2);
        assertThat(response.getBody().getCount()).isEqualTo(2);
        assertThat(response.getBody().getMessage()).isEqualTo("Success");
    }

    @Test
    void getJobs_callsServiceExactlyOnce() {
        when(jobService.getJobsFiltered(any())).thenReturn(List.of());

        jobController.getJobs(null, "ASC", "salary", null, null, null, null);

        verify(jobService, times(1)).getJobsFiltered(any());
    }

    // Failed cases

    @Test
    void getJobs_serviceThrowsIllegalArgument_propagatesException() {
        when(jobService.getJobsFiltered(any()))
                .thenThrow(new IllegalArgumentException("Unable to locate Attribute with the given name [invalid_col]"));

        assertThatThrownBy(() -> jobController.getJobs(
                null, "ASC", "invalid_col",
                null, null, null, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("invalid_col");
    }

    @Test
    void getJobs_serviceThrowsRuntimeException_propagatesException() {
        when(jobService.getJobsFiltered(any()))
                .thenThrow(new RuntimeException("Unexpected database error"));

        assertThatThrownBy(() -> jobController.getJobs(
                null, "ASC", "salary",
                null, null, null, null))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Unexpected database error");
    }

    @Test
    void getJobs_serviceThrowsIllegalArgument_serviceNotCalledAgain() {
        when(jobService.getJobsFiltered(any()))
                .thenThrow(new IllegalArgumentException("bad field"));

        try {
            jobController.getJobs(null, "ASC", "bad_field", null, null, null, null);
        } catch (IllegalArgumentException ignored) {}

        verify(jobService, times(1)).getJobsFiltered(any());
        verifyNoMoreInteractions(jobService);
    }
}
