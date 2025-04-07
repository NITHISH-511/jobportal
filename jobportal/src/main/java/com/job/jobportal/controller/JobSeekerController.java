package com.job.jobportal.controller;

import java.util.*;

//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.job.jobportal.entity.JobSeeker;
import com.job.jobportal.service.JobSeekerService;

@RestController
@RequestMapping("/api/jobseekers")
public class JobSeekerController {

    private final JobSeekerService jobSeekerService;
    private final ObjectMapper objectMapper;

    
    public JobSeekerController(JobSeekerService jobSeekerService, ObjectMapper objectMapper) {
        this.jobSeekerService = jobSeekerService;
        this.objectMapper = objectMapper;
    }

    // --------------------------- GET ALL JOB SEEKERS
    // -----------------------------------
    @GetMapping
    public ResponseEntity<List<JobSeeker>> getAllJobSeekers() {
        List<JobSeeker> jobSeekers = jobSeekerService.getAllJobSeekers();
        return new ResponseEntity<>(jobSeekers, HttpStatus.OK);
    }

    // --------------------------- GET JOB SEEKER BY ID
    // -----------------------------------
    @GetMapping("/{id}")
    public ResponseEntity<JobSeeker> getJobSeekerById(@PathVariable Long id) {
        Optional<JobSeeker> jobSeeker = jobSeekerService.getJobSeekerById(id);
        return jobSeeker.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // --------------------------- GET JOB SEEKER BY EMAIL
    // -----------------------------------
    @GetMapping("/by-email/{email}")
    public ResponseEntity<JobSeeker> getJobSeekerByEmail(@PathVariable String email) {
        Optional<JobSeeker> jobSeeker = jobSeekerService.getJobSeekerByEmail(email);
        return jobSeeker.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    // --------------------------- GET PAGINATED LIST OF JOB SEEKERS
    // -----------------------------------
    @GetMapping("/list")
    public ResponseEntity<Map<String, Object>> getJobSeekersPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {
        Page<JobSeeker> jobSeekers = jobSeekerService.getJobSeekersPaginated(page, size, sortBy, sortDir);

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("content", jobSeekers.getContent());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // --------------------------- CREATE JOB SEEKER (Single & Bulk)
    // -----------------------------------
    @PostMapping
    public ResponseEntity<?> createJobSeeker(@RequestBody Object requestBody) {
        try {
            String json = objectMapper.writeValueAsString(requestBody);

            if (json.trim().startsWith("[")) {
                List<JobSeeker> jobSeekers = objectMapper.readValue(json,
                        objectMapper.getTypeFactory().constructCollectionType(List.class, JobSeeker.class));
                List<JobSeeker> savedJobSeekers = jobSeekerService.createJobSeekers(jobSeekers);
                return new ResponseEntity<>(savedJobSeekers, HttpStatus.CREATED);
            } else {
                JobSeeker jobSeeker = objectMapper.readValue(json, JobSeeker.class);
                JobSeeker savedJobSeeker = jobSeekerService.createJobSeeker(jobSeeker);
                return new ResponseEntity<>(savedJobSeeker, HttpStatus.CREATED);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Error processing request: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // --------------------------- UPDATE JOB SEEKER
    // -----------------------------------
    @PutMapping("/{id}")
    public ResponseEntity<JobSeeker> updateJobSeeker(@PathVariable Long id, @RequestBody JobSeeker jobSeeker) {
        try {
            JobSeeker updatedJobSeeker = jobSeekerService.updateJobSeeker(id, jobSeeker);
            return new ResponseEntity<>(updatedJobSeeker, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // --------------------------- DELETE JOB SEEKER
    // -----------------------------------
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJobSeeker(@PathVariable Long id) {
        try {
            jobSeekerService.deleteJobSeeker(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}