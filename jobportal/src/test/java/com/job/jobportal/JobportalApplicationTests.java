package com.job.jobportal;

import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.job.jobportal.controller.JobController;
import com.job.jobportal.entity.*;
import com.job.jobportal.repository.CompanyRepository;
import com.job.jobportal.repository.JobRepository;
import com.job.jobportal.repository.JobSeekerRepository;
import com.job.jobportal.service.JobService;

import org.hibernate.query.Page;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

import java.util.Arrays;
import java.util.List;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;

import static org.hamcrest.Matchers.greaterThan;

import static org.junit.jupiter.api.Assertions.assertTrue;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class JobPortalIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private Job job;
    private JobSeeker jobSeeker;
    private Company company;
    private Admin admin;
    private JobApplication jobApplication;
    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private JobService jobService;
    @Autowired
    private JobController jobcontroller;
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private JobSeekerRepository jobSeekerRepository;

    @BeforeEach
    // void setup() throws Exception {
    // // Initialize entities
    // job = new Job(null, "Software Engineer", "Develop software applications",
    // "Remote", 80000.0);
    // jobSeeker = new JobSeeker(null, "John Doe", "john.doe@example.com",
    // "1234567890", "Java, Spring Boot");
    // company = new Company(null, "TechCorp", "New York", "Technology", "Innovating
    // technology solutions");
    // admin = new Admin(null, "admin", "admin123");

    // // Create entities in the database
    // createEntity("/api/jobs", job);
    // createEntity("/api/jobseekers", jobSeeker);
    // createEntity("/api/companies", company);
    // createEntity("/api/admins", admin);
    // }

    void setup() throws Exception {
        // Initialize entities
        job = new Job(null, "Software Engineer", "Develop software applications", "Remote", 80000.0);
        jobSeeker = new JobSeeker(null, "John Doe", "john.doe@example.com", "1234567890", "Java, Spring Boot");
        company = new Company(null, "TechCorp", "New York", "Technology", "Innovating technology solutions");
        admin = new Admin(null, "admin", "admin123");

        // Additional entities for testing
        Job job2 = new Job(null, "Data Scientist", "Analyze data", "Remote", 95000.0);
        Job job3 = new Job(null, "Product Manager", "Oversee product development", "San Francisco", 120000.0);
        Job job4 = new Job(null, "UX Designer", "Design user interfaces", "Remote", 85000.0);
        Job job5 = new Job(null, "QA Engineer", "Test software products", "New York", 70000.0);
        Job job6 = new Job(null, "DevOps Engineer", "Automate and manage infrastructure", "Chicago", 110000.0);

        JobSeeker jobSeeker2 = new JobSeeker(null, "Jane Smith", "jane.smith@example.com", "0987654321",
                "Python, Machine Learning");
        JobSeeker jobSeeker3 = new JobSeeker(null, "Alice Johnson", "alice.johnson@example.com", "1122334455",
                "Product Management, Agile");

        Company company2 = new Company(null, "InnovateTech", "Los Angeles", "Software", "Innovative solutions");
        Company company3 = new Company(null, "GreenTech", "San Francisco", "Sustainability", "Eco-friendly products");

        Admin admin2 = new Admin(null, "manager", "manager123");
        Admin admin3 = new Admin(null, "hradmin", "hradmin123");

        // Create entities in the database
        createEntity("/api/jobs", job);
        createEntity("/api/jobs", job2);
        createEntity("/api/jobs", job3);
        createEntity("/api/jobs", job4);
        createEntity("/api/jobs", job5);
        createEntity("/api/jobs", job6);

        createEntity("/api/jobseekers", jobSeeker);
        createEntity("/api/jobseekers", jobSeeker2);
        createEntity("/api/jobseekers", jobSeeker3);

        createEntity("/api/companies", company);
        createEntity("/api/companies", company2);
        createEntity("/api/companies", company3);

        createEntity("/api/admins", admin);
        createEntity("/api/admins", admin2);
        createEntity("/api/admins", admin3);

        // Create JobApplication in the database
        // jobApplication = new JobApplication(null, "2025-01-02", "Pending", job,
        // jobSeeker, company);
        // createEntity("/api/job-applications", jobApplication);
    }

    private <T> void createEntity(String url, T entity) throws Exception {
        mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(entity)))
                .andExpect(status().isOk());
    }

    // -------------------- Job Tests -------------------- //

    @Test
    void Annotation_testCompanyHasJSONIgnoreAnnotations() throws Exception {
        // Path to the Book entity file
        Path entityFilePath = Paths.get("src/main/java/com/job/jobportal/entity/Company.java");

        // Read the content of the entity file as a string
        String entityFileContent = Files.readString(entityFilePath);

        // Check if Lombok annotations like @Data exist
        assertTrue(entityFileContent.contains("@JsonIgnore"), "Company entity should contain @JsonIgnore annotation");
    }

    @Test
    void Annotation_testJobHasJSONIgnoreAnnotations() throws Exception {
        // Path to the Book entity file
        Path entityFilePath = Paths.get("src/main/java/com/job/jobportal/entity/Job.java");

        // Read the content of the entity file as a string
        String entityFileContent = Files.readString(entityFilePath);

        // Check if Lombok annotations like @Data exist
        assertTrue(entityFileContent.contains("@JsonIgnore"), "Job entity should contain @JsonIgnore annotation");
    }

    @Test
    void Annotation_testJobSeekerHasJSONIgnoreAnnotations() throws Exception {
        // Path to the Book entity file
        Path entityFilePath = Paths.get("src/main/java/com/job/jobportal/entity/JobSeeker.java");

        // Read the content of the entity file as a string
        String entityFileContent = Files.readString(entityFilePath);

        // Check if Lombok annotations like @Data exist
        assertTrue(entityFileContent.contains("@JsonIgnore"), "JobSeeker entity should contain @JsonIgnore annotation");
    }

    @Test
    void Annotation_testCompanyHasEntityAnnotations() throws Exception {
        // Path to the Book entity file
        Path entityFilePath = Paths.get("src/main/java/com/job/jobportal/entity/Company.java");

        // Read the content of the entity file as a string
        String entityFileContent = Files.readString(entityFilePath);

        // Check if Lombok annotations like @Data exist
        assertTrue(entityFileContent.contains("@Entity"), "Company entity should contain @Entity annotation");
    }

    @Test
    void Annotation_testJobHasNoArgumentsAnnotations() throws Exception {
        // Path to the Book entity file
        Path entityFilePath = Paths.get("src/main/java/com/job/jobportal/entity/Job.java");

        // Read the content of the entity file as a string
        String entityFileContent = Files.readString(entityFilePath);

        // Check if Lombok annotations like @Data exist
        assertTrue(entityFileContent.contains("@NoArgsConstructor"),
                "Job entity should contain @NoArgsConstructor annotation");
    }

    @Test
    void Annotation_testJobSeekerHasDataAnnotations() throws Exception {
        // Path to the Book entity file
        Path entityFilePath = Paths.get("src/main/java/com/job/jobportal/entity/JobSeeker.java");

        // Read the content of the entity file as a string
        String entityFileContent = Files.readString(entityFilePath);

        // Check if Lombok annotations like @Data exist
        assertTrue(entityFileContent.contains("@Data"), "JobSeeker entity should contain @Data annotation");
    }
    @Test
    void Annotation_testJobApplicationHasDataAnnotations() throws Exception {
        // Path to the Book entity file
        Path entityFilePath = Paths.get("src/main/java/com/job/jobportal/entity/JobApplication.java");

        // Read the content of the entity file as a string
        String entityFileContent = Files.readString(entityFilePath);

        // Check if Lombok annotations like @Data exist
        assertTrue(entityFileContent.contains("@Data"), "JobApplication entity should contain @Data annotation");
    }
    @Test
    void Annotation_testJobApplicationHasNoArgumentsAnnotations() throws Exception {
        // Path to the Book entity file
        Path entityFilePath = Paths.get("src/main/java/com/job/jobportal/entity/JobApplication.java");

        // Read the content of the entity file as a string
        String entityFileContent = Files.readString(entityFilePath);

        // Check if Lombok annotations like @Data exist
        assertTrue(entityFileContent.contains("@NoArgsConstructor"), "JobApplication entity should contain @NoArguments annotation");
    }
    

    @Test
    void Repository_testAdminRepository() throws Exception {
        // Path to the Author entity file
        Path entityFilePath = Paths.get("src/main/java/com/job/jobportal/repository/AdminRepository.java");

        // Assert that the file exists
        assertTrue(Files.exists(entityFilePath), "AdminRepository file should exist");
    }

    @Test
    void Repository_testCompanyRepository() throws Exception {
        // Path to the Author entity file
        Path entityFilePath = Paths.get("src/main/java/com/job/jobportal/repository/CompanyRepository.java");

        // Assert that the file exists
        assertTrue(Files.exists(entityFilePath), "CompanyRepository file should exist");
    }

    @Test
    void Repository_testJobRepository() throws Exception {
        // Path to the Author entity file
        Path entityFilePath = Paths.get("src/main/java/com/job/jobportal/repository/JobRepository.java");

        // Assert that the file exists
        assertTrue(Files.exists(entityFilePath), "JobRepository file should exist");
    }

    @Test
    void Repository_testJobSeekerRepository() throws Exception {
        // Path to the Author entity file
        Path entityFilePath = Paths.get("src/main/java/com/job/jobportal/repository/JobSeekerRepository.java");

        // Assert that the file exists
        assertTrue(Files.exists(entityFilePath), "JobSeekerRepository file should exist");
    }
    @Test
    void Repository_testJobApplicationRepository() throws Exception {
        // Path to the Author entity file
        Path entityFilePath = Paths.get("src/main/java/com/job/jobportal/repository/JobApplicationRepository.java");

        // Assert that the file exists
        assertTrue(Files.exists(entityFilePath), "JobApplicationRepository file should exist");
    }
    // @Test
    // void CRUD_getJobById() throws Exception {
    // mockMvc.perform(get("/api/jobs/1"))
    // .andExpect(status().isOk());
    // }

    @Test
    void CRUD_getAllJobs() throws Exception {
        mockMvc.perform(get("/api/jobs"))
                .andExpect(status().isOk());
    }

    @Test
    void CRUD_updateJob() throws Exception {
        job.setDescription("Updated job description");

        ResultActions response = mockMvc.perform(put("/api/jobs/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(job)));

        response.andExpect(status().isOk());
    }

    @Test
    void CRUD_deleteJob() throws Exception {
        mockMvc.perform(delete("/api/jobs/1"))
                .andExpect(status().isNoContent());
    }

    // -------------------- JobSeeker Tests -------------------- //

    @Test
    void CRUD_createJobSeeker() throws Exception {
        ResultActions response = mockMvc.perform(post("/api/jobseekers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(jobSeeker)));

        response.andExpect(status().isOk());
    }

    @Test
    void CRUD_getJobSeekerById() throws Exception {
        mockMvc.perform(get("/api/jobseekers/1"))
                .andExpect(status().isOk());
    }

    @Test
    void CRUD_getAllJobSeekers() throws Exception {
        mockMvc.perform(get("/api/jobseekers"))
                .andExpect(status().isOk());
    }

    @Test
    void CRUD_updateJobSeeker() throws Exception {
        jobSeeker.setName("Updated Name");

        ResultActions response = mockMvc.perform(put("/api/jobseekers/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(jobSeeker)));

        response.andExpect(status().isOk());
    }

    // -------------------- Company Tests -------------------- //

    @Test
    void CRUD_createCompany() throws Exception {
        ResultActions response = mockMvc.perform(post("/api/companies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(company)));

        response.andExpect(status().isOk());
    }

    @Test
    void CRUD_getCompanyById() throws Exception {
        mockMvc.perform(get("/api/companies/1"))
                .andExpect(status().isOk());
    }

    @Test
    void CRUD_updateCompany() throws Exception {
        company.setDescription("Updated Description");

        ResultActions response = mockMvc.perform(put("/api/companies/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(company)));

        response.andExpect(status().isOk());
    }

    @Test
    void CRUD_getAllCompanies() throws Exception {
        mockMvc.perform(get("/api/companies"))
                .andExpect(status().isOk());
    }

    @Test
    void CRUD_deleteCompany() throws Exception {
        mockMvc.perform(delete("/api/companies/1"))
                .andExpect(status().isNoContent());
    }

    // -------------------- Admin Tests -------------------- //

    @Test
    void CRUD_getAdminById() throws Exception {
        mockMvc.perform(get("/api/admins/1"))
                .andExpect(status().isOk());
    }

    @Test
    void CRUD_getAllAdmins() throws Exception {
        mockMvc.perform(get("/api/admins"))
                .andExpect(status().isOk());
    }

    @Test
    void CRUD_updateAdmin() throws Exception {
        admin.setPassword("newPassword123");

        ResultActions response = mockMvc.perform(put("/api/admins/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(admin)));

        response.andExpect(status().isOk());
    }

    // .......................................................//
    @Test
    void CRUD_createJobApplication() throws Exception {
        // Create the associated entities with updated names
        Company company123 = new Company(null, "TechCorp", "New York", "Technology", "Innovating technology solutions");
        Job job123 = new Job(null, "Software Engineer", "Develop software applications", "Remote", 80000.0);
        JobSeeker jobSeeker123 = new JobSeeker(null, "John Doe", "john.doe@example.com", "1234567890", "Java, Spring Boot");
    
        // Persist the associated entities in the database first
        company123 = companyRepository.save(company123);
        job123 = jobRepository.save(job123);
        jobSeeker123 = jobSeekerRepository.save(jobSeeker123);
    
        // Create the JobApplication entity with updated names
        JobApplication jobApplication123 = new JobApplication(null, "2025-01-03", "Submitted", job123, jobSeeker123, company123);
    
        // Perform the POST request to create the JobApplication
        mockMvc.perform(post("/api/job-applications")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(jobApplication123)))
                .andExpect(status().isOk());
    }
    
  
    
    @Test
    void CRUD_getAllJobApplications() throws Exception {
        mockMvc.perform(get("/api/job-applications"))
                .andExpect(status().isOk());
    }

    
  
       
    
    @Test
    void CRUD_deleteJobApplication() throws Exception {
        // Ensure that the JobApplication with ID 1 exists before deleting
        mockMvc.perform(delete("/api/job-applications/1"))
                .andExpect(status().isNoContent()); // Expect 204 No Content for successful delete
    }
    
    void JPQL_getCompaniesByIndustry() throws Exception {
        // Define the industry to test
        String industry = "Technology";

        // Perform a GET request to the /api/companies/by-industry endpoint with the
        // industry parameter
        mockMvc.perform(get("/api/companies/by-industry")
                .param("industry", industry))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray()) // Assert that the response is an array
                .andExpect(jsonPath("$.length()").value(greaterThan(0))) // Assert that the array is not empty
                .andExpect(jsonPath("$[0].industry").value(industry)); // Assert that the industry matches
    }

    void JPQL_getCompaniesByIndustry_EmptyResponse() throws Exception {
        // Define an industry that does not exist in the database
        String nonExistentIndustry = "NonExistentIndustry";

        // Perform a GET request to the /api/companies/by-industry endpoint with the
        // non-existent industry
        mockMvc.perform(get("/api/companies/by-industry")
                .param("industry", nonExistentIndustry))
                .andExpect(status().isOk()) // The status should still be 200 (OK)
                .andExpect(jsonPath("$").isArray()) // Assert that the response is an array
                .andExpect(jsonPath("$.length()").value(0)); // Assert that the array is empty
    }

    @Test
    void PaginateSorting_testPaginateJobControllers() throws Exception {
        // Path to the Book entity file
        Path entityFilePath = Paths.get("src/main/java/com/job/jobportal/controller/JobController.java");

        // Read the content of the entity file as a string
        String entityFileContent = Files.readString(entityFilePath);

        // Check if Lombok annotations like @Data exist
        assertTrue(entityFileContent.contains("Page<Job>"), "Job Controller should have pagination implemented");
    }

    @Test
    void PaginateSorting_testPaginateAuthorsService() throws Exception {
        // Path to the Book entity file
        Path entityFilePath = Paths.get("src/main/java/com/job/jobportal/service/JobService.java");

        // Read the content of the entity file as a string
        String entityFileContent = Files.readString(entityFilePath);

        // Check if Lombok annotations like @Data exist
        assertTrue(entityFileContent.contains("Pageable"),
                "Job Service should have Pageable object to implement pagination");
    }
    @Test
    void Mapping_testEntityHasManyToOne() throws Exception {
        // Path to the entity file
        Path entityFilePath = Paths.get("src/main/java/com/job/jobportal/entity/JobApplication.java");
 
        // Read the content of the entity file as a string
        String entityFileContent = Files.readString(entityFilePath);
 
        // Check if the @ManyToOne annotation exists in the entity file
        assertTrue(entityFileContent.contains("@ManyToOne"), "Entity should contain @ManyToMany annotation");
    }@Test
    void Mapping_testEntityJoinColumn() throws Exception {
        // Path to the entity file
        Path entityFilePath = Paths.get("src/main/java/com/job/jobportal/entity/JobApplication.java");
 
        // Read the content of the entity file as a string
        String entityFileContent = Files.readString(entityFilePath);
 
        // Check if the @ManyToOne annotation exists in the entity file
        assertTrue(entityFileContent.contains("@JoinColumn"), "Entity should contain @ManyToMany annotation");
    }

    @Test
    public void SwaggerUI_testConfigurationFolder() {
        String directoryPath = "src/main/java/com/job/jobportal/config"; // Replace with the path to your directory
        File directory = new File(directoryPath);
        assertTrue(directory.exists() && directory.isDirectory());
    }

    @Test
    void SwaggerUI_testSwaggerConfigFile() {

        String filePath = "src/main/java/com/job/jobportal/config/SwaggerConfig.java";

        // Replace with the path to your file

        File file = new File(filePath);

        assertTrue(file.exists() && file.isFile());

    }

    @Test
    public void LOG_testLogFolderAndFileCreation() {
        String LOG_FOLDER_PATH = "logs";
        String LOG_FILE_PATH = "logs/application.log";
        // Check if the "logs" folder exists
        File logFolder = new File(LOG_FOLDER_PATH);
        assertTrue(logFolder.exists(), "Log folder should be created");

        // Check if the "application.log" file exists inside the "logs" folder
        File logFile = new File(LOG_FILE_PATH);
        assertTrue(logFile.exists(), "Log file should be created inside 'logs' folder");
    }

    @Test
    void AOP_testAOPConfigFile() {

        String filePath = "src/main/java/com/job/jobportal/aspect/LoggingAspect.java";

        // Replace with the path to your file

        File file = new File(filePath);

        assertTrue(file.exists() && file.isFile());

    }

    @Test
    void AOP_testAOPConfigFileAspect() throws Exception {
        // Path to the Book entity file
        Path entityFilePath = Paths.get("src/main/java/com/job/jobportal/aspect/LoggingAspect.java");

        // Read the content of the entity file as a string
        String entityFileContent = Files.readString(entityFilePath);

        // Check if Lombok annotations like @Data exist
        assertTrue(entityFileContent.contains("Aspect"), "Implement Aspect in LoggingAspect.java");
    }

}
