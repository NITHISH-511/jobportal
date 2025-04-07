package com.job.jobportal.service;

import com.job.jobportal.entity.Company;
import com.job.jobportal.entity.Job;
import com.job.jobportal.repository.CompanyRepository;
import com.job.jobportal.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class JobServiceImpl implements JobService {

    @Autowired
    private JobRepository jobRepository;
    

    @Override
    public List<Job> getAllJobs() {
        return jobRepository.findAll();
    }

    @Override
    public Optional<Job> getJobById(Long id) {
        return jobRepository.findById(id);
    }

    

    @Override
    public Job updateJob(Long id, Job job) {
        if (jobRepository.existsById(id)) {
            job.setId(id);
            return jobRepository.save(job);
        } else {
            throw new RuntimeException("Job not found");
        }
    }

    @Override
    public void deleteJob(Long id) {
        if (jobRepository.existsById(id)) {
            jobRepository.deleteById(id);
        } else {
            throw new RuntimeException("Job not found");
        }
    }

    @Override
    public Page<Job> getJobsPaginated(int page, int size, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return jobRepository.findAll(pageable);
    }

    @Override
    public Job createJob(Job job) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createJob'");
    }
}
