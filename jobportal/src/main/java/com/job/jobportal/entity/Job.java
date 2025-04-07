package com.job.jobportal.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
@Data
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Ensure auto-incremented IDs
    private Long id;

    private String title;
    private String description;
    private String location;
    private Double salary;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company; // Each job belongs to a single company

    @OneToMany(mappedBy = "job", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<JobApplication> jobApplications;

    public Job() {
    }

    public Job(String title, String description, String location, Double salary, Company company) {
        this.title = title;
        this.description = description;
        this.location = location;
        this.salary = salary;
        this.company = company;
    }
}
