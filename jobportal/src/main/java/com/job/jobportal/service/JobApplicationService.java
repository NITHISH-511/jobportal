package com.job.jobportal.service;

import com.job.jobportal.entity.JobApplication;
import java.util.List;

public interface JobApplicationService {
    List<JobApplication> getAllApplications();

    JobApplication getApplicationById(Long id);

    JobApplication applyForJob(Long jobSeekerId, Long jobId, Long companyId, String status);

    void deleteApplication(Long id);
}
