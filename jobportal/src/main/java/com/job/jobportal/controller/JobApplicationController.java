package com.job.jobportal.controller;

import com.job.jobportal.entity.JobApplication;
import com.job.jobportal.service.JobApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/job-applications")
public class JobApplicationController {

    @Autowired
    private JobApplicationService jobApplicationService;

    // ✅ Get all applications
    @GetMapping
    public List<JobApplication> getAllApplications() {
        return jobApplicationService.getAllApplications();
    }

    // ✅ Get application by ID
    @GetMapping("/{id}")
    public JobApplication getApplicationById(@PathVariable Long id) {
        return jobApplicationService.getApplicationById(id);
    }

    // ✅ Apply for a job
    @PostMapping("/apply")
    public ResponseEntity<?> applyForJob(
            @RequestParam Long jobSeekerId,
            @RequestParam Long jobId,
            @RequestParam Long companyId,
            @RequestParam String status) {
        return ResponseEntity.ok("Received application");
    }




    // ✅ Delete application
    @DeleteMapping("/{id}")
    public String deleteApplication(@PathVariable Long id) {
        jobApplicationService.deleteApplication(id);
        return "Job Application deleted successfully";
    }
}
