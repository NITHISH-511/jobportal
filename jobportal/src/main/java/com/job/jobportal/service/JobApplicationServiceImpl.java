package com.job.jobportal.service;

import com.job.jobportal.entity.*;
import com.job.jobportal.repository.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobApplicationServiceImpl implements JobApplicationService {

    @Autowired
    private JobApplicationRepository jobApplicationRepository;

    @Autowired
    private JobSeekerRepository jobSeekerRepository;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private CompanyRepository companyRepository;

    // ✅ 1. Get all job applications
    @Override
    public List<JobApplication> getAllApplications() {
        return jobApplicationRepository.findAll();
    }

    // ✅ 2. Get job application by ID
    @Override
    public JobApplication getApplicationById(Long id) {
        return jobApplicationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Job Application not found with ID: " + id));
    }

    // ✅ 3. Apply for a job
    @Override
    public JobApplication applyForJob(Long jobSeekerId, Long jobId, Long companyId, String status) {
        JobSeeker jobSeeker = jobSeekerRepository.findById(jobSeekerId)
                .orElseThrow(() -> new RuntimeException("Job Seeker not found with ID: " + jobSeekerId));

        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job not found with ID: " + jobId));

        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new RuntimeException("Company not found with ID: " + companyId));

        JobApplication application = new JobApplication(jobSeeker, job, company, status);
        return jobApplicationRepository.save(application);
    }

    // ✅ 4. Delete job application
    @Override
    public void deleteApplication(Long id) {
        if (!jobApplicationRepository.existsById(id)) {
            throw new RuntimeException("Job Application not found with ID: " + id);
        }
        jobApplicationRepository.deleteById(id);
    }
}
