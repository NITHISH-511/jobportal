package com.job.jobportal.repository;

import com.job.jobportal.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobApplicationRepository extends JpaRepository<JobApplication, Long> {

   
    List<JobApplication> findByJobSeeker(JobSeeker jobSeeker);

    
    List<JobApplication> findByJob(Job job);

    
    List<JobApplication> findByCompany(Company company);
}
