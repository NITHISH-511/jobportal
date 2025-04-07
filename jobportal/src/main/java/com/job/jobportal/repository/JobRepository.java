package com.job.jobportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.job.jobportal.entity.Job;

public interface JobRepository extends JpaRepository<Job, Long> {
    
}
