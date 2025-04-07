package com.job.jobportal.service;

import com.job.jobportal.entity.Job;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface JobService {
    List<Job> getAllJobs();

    Optional<Job> getJobById(Long id);

    Job createJob(Job job);

    Job updateJob(Long id, Job job);

    void deleteJob(Long id);

    Page<Job> getJobsPaginated(int page, int size, String sortBy, String sortDir);
}
