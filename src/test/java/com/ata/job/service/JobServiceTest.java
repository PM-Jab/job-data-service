package com.ata.job.service;

import com.ata.job.model.JobRequestParam;
import com.ata.job.model.JobResponseBody;
import com.ata.job.repository.JobRepository;
import com.ata.job.repository.entity.Job;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JobServiceTest {

    @Mock
    private JobRepository jobRepository;

    @InjectMocks
    private JobService jobService;

    private Job mockJob;

    @BeforeEach
    void setUp() {
        mockJob = new Job(1L, "3/21/16 12:00", "Google", "Mountain View",
                "Software Engineer", "3", "5", 120000.0,
                10000.0, 15000.0, 50000.0, "Male", null);
    }

    // Happy cases

    @Test
    void getJobsFiltered_returnsListMappedToResponseBody() {
        when(jobRepository.findAll(any(Specification.class), any(Sort.class)))
                .thenReturn(List.of(mockJob));

        JobRequestParam req = JobRequestParam.builder()
                .fields("job_title")
                .sortType("ASC")
                .build();

        List<JobResponseBody> result = jobService.getJobsFiltered(req);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getJobTitle()).isEqualTo("Software Engineer");
        assertThat(result.get(0).getEmployer()).isEqualTo("Google");
        assertThat(result.get(0).getSalary()).isEqualTo("120000.0");
    }

    @Test
    void getJobsFiltered_returnsEmptyList_whenRepositoryReturnsEmpty() {
        when(jobRepository.findAll(any(Specification.class), any(Sort.class)))
                .thenReturn(List.of());

        JobRequestParam req = JobRequestParam.builder()
                .fields("salary")
                .sortType("ASC")
                .build();

        List<JobResponseBody> result = jobService.getJobsFiltered(req);

        assertThat(result).isEmpty();
    }

    @Test
    void getJobsFiltered_withNoFields_doesNotApplyForFieldsSpec() {
        when(jobRepository.findAll(any(Specification.class), any(Sort.class)))
                .thenReturn(List.of(mockJob));

        // fields is null — only fromParams spec applied
        JobRequestParam req = JobRequestParam.builder()
                .minSalary(50000.0)
                .sortType("ASC")
                .build();

        List<JobResponseBody> result = jobService.getJobsFiltered(req);

        assertThat(result).hasSize(1);
        verify(jobRepository, times(1)).findAll(any(Specification.class), any(Sort.class));
    }

    @Test
    void getJobsFiltered_withBlankFields_doesNotApplyForFieldsSpec() {
        when(jobRepository.findAll(any(Specification.class), any(Sort.class)))
                .thenReturn(List.of());

        JobRequestParam req = JobRequestParam.builder()
                .fields("   ")
                .sortType("ASC")
                .build();

        jobService.getJobsFiltered(req);

        verify(jobRepository, times(1)).findAll(any(Specification.class), any(Sort.class));
    }

    @Test
    void getJobsFiltered_mapsNullSalaryToNull() {
        Job jobWithNullSalary = new Job(2L, null, null, null,
                "Intern", null, null, null,
                null, null, null, null, null);
        when(jobRepository.findAll(any(Specification.class), any(Sort.class)))
                .thenReturn(List.of(jobWithNullSalary));

        JobRequestParam req = JobRequestParam.builder().fields("salary").sortType("ASC").build();

        List<JobResponseBody> result = jobService.getJobsFiltered(req);

        assertThat(result.get(0).getSalary()).isNull();
        assertThat(result.get(0).getSigningBonus()).isNull();
        assertThat(result.get(0).getAnnualBonus()).isNull();
        assertThat(result.get(0).getAnnualStockValueBonus()).isNull();
    }

    @Test
    void getJobsFiltered_withSortDesc_passesDescSortToRepository() {
        when(jobRepository.findAll(any(Specification.class), any(Sort.class)))
                .thenReturn(List.of());

        JobRequestParam req = JobRequestParam.builder()
                .fields("salary")
                .sort("salary")
                .sortType("DESC")
                .build();

        jobService.getJobsFiltered(req);

        verify(jobRepository).findAll(
                any(Specification.class),
                eq(Sort.by(Sort.Direction.DESC, "salary"))
        );
    }

    @Test
    void getJobsFiltered_withNoSort_defaultsSortByTimestamp() {
        when(jobRepository.findAll(any(Specification.class), any(Sort.class)))
                .thenReturn(List.of());

        JobRequestParam req = JobRequestParam.builder()
                .fields("salary")
                .sortType("ASC")
                .build();  // sort is null

        jobService.getJobsFiltered(req);

        verify(jobRepository).findAll(
                any(Specification.class),
                eq(Sort.by(Sort.Direction.ASC, "timestamp"))
        );
    }

    @Test
    void getJobsFiltered_callsRepositoryExactlyOnce() {
        when(jobRepository.findAll(any(Specification.class), any(Sort.class)))
                .thenReturn(List.of());

        JobRequestParam req = JobRequestParam.builder().fields("salary").sortType("ASC").build();

        jobService.getJobsFiltered(req);

        verify(jobRepository, times(1)).findAll(any(Specification.class), any(Sort.class));
        verifyNoMoreInteractions(jobRepository);
    }

    // Failed cases 

    @Test
    void getJobsFiltered_repositoryThrowsException_propagatesException() {
        when(jobRepository.findAll(any(Specification.class), any(Sort.class)))
                .thenThrow(new RuntimeException("DB connection failed"));

        JobRequestParam req = JobRequestParam.builder().fields("salary").sortType("ASC").build();

        assertThatThrownBy(() -> jobService.getJobsFiltered(req))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("DB connection failed");
    }

    @Test
    void getJobsFiltered_invalidSortDirection_throwsIllegalArgumentException() {
        JobRequestParam req = JobRequestParam.builder()
                .fields("salary")
                .sort("salary")
                .sortType("INVALID")
                .build();

        assertThatThrownBy(() -> jobService.getJobsFiltered(req))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
