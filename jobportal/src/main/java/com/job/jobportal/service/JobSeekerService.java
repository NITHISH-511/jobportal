package com.job.jobportal.service;

import com.job.jobportal.entity.JobSeeker;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface JobSeekerService {
    List<JobSeeker> getAllJobSeekers();
    JobSeeker createJobSeeker(JobSeeker jobSeeker);
    List<JobSeeker> createJobSeekers(List<JobSeeker> jobSeekers);
    Optional<JobSeeker> getJobSeekerById(Long id);
    Optional<JobSeeker> getJobSeekerByEmail(String email);
    JobSeeker updateJobSeeker(Long id, JobSeeker jobSeeker);
    void deleteJobSeeker(Long id);
    Page<JobSeeker> getJobSeekersPaginated(int page, int size, String sortBy, String sortDir);
}


