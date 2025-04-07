package com.job.jobportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.job.jobportal.entity.JobSeeker;
import java.util.Optional;

public interface JobSeekerRepository extends JpaRepository<JobSeeker, Long> {

    @Query("SELECT j FROM JobSeeker j WHERE j.email = :email")
    Optional<JobSeeker> findByEmail(@Param("email") String email);
}
