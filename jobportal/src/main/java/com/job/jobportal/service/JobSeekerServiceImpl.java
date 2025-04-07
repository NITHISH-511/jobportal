package com.job.jobportal.service;

//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.job.jobportal.entity.JobSeeker;
import com.job.jobportal.repository.JobSeekerRepository;
import org.springframework.data.domain.*;

import java.util.List;
import java.util.Optional;

@Service
public class JobSeekerServiceImpl implements JobSeekerService {

    private final JobSeekerRepository jobSeekerRepository;

    public JobSeekerServiceImpl(JobSeekerRepository jobSeekerRepository) {
        this.jobSeekerRepository = jobSeekerRepository;
    }

    @Override
    public List<JobSeeker> getAllJobSeekers() {
        return jobSeekerRepository.findAll();
    }

    @Transactional
    @Override
    public JobSeeker createJobSeeker(JobSeeker jobSeeker) {
        return jobSeekerRepository.save(jobSeeker);
    }

    @Override
    public List<JobSeeker> createJobSeekers(List<JobSeeker> jobSeekers) {
        return jobSeekerRepository.saveAll(jobSeekers);
    }

    @Override
    public Optional<JobSeeker> getJobSeekerById(Long id) {
        return jobSeekerRepository.findById(id);
    }

    @Override
    public Optional<JobSeeker> getJobSeekerByEmail(String email) {
        return jobSeekerRepository.findByEmail(email); // ✅ Uses JPQL query
    }

    @Override
    public JobSeeker updateJobSeeker(Long id, JobSeeker jobSeeker) {
        if (jobSeekerRepository.existsById(id)) {
            jobSeeker.setId(id); // Ensure the ID is set before updating
            return jobSeekerRepository.save(jobSeeker);
        } else {
            throw new RuntimeException("JobSeeker not found");
        }
    }

    @Override
    public void deleteJobSeeker(Long id) {
        if (jobSeekerRepository.existsById(id)) {
            jobSeekerRepository.deleteById(id);
        } else {
            throw new RuntimeException("JobSeeker not found");
        }
    }

    @Override
    public Page<JobSeeker> getJobSeekersPaginated(int page, int size, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return jobSeekerRepository.findAll(pageable);
    }
}
