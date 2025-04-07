package com.job.jobportal.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.job.jobportal.entity.Company;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
    
}
