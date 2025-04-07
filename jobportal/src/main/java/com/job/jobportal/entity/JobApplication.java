package com.job.jobportal.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class JobApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "job_seeker_id", nullable = false)
    private JobSeeker jobSeeker;

    @ManyToOne
    @JoinColumn(name = "job_id", nullable = false)
    private Job job;

    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    private String applicationStatus;

    public JobApplication() {
    }

    public JobApplication(JobSeeker jobSeeker, Job job, Company company, String applicationStatus) {
        this.jobSeeker = jobSeeker;
        this.job = job;
        this.company = company;
        this.applicationStatus = applicationStatus;
    }

    // ✅ Returns the Job Seeker ID safely
    public Long getJobSeekerId() {
        return jobSeeker != null ? jobSeeker.getId() : null;
    }
}
