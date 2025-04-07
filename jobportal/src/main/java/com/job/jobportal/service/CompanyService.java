package com.job.jobportal.service;

import com.job.jobportal.entity.Company;
import java.util.List;

public interface CompanyService {
    List<Company> getAllCompanies();

    Company getCompanyById(Long id);

    Company saveCompany(Company company);

    Company updateCompany(Long id, Company company);

    void deleteCompany(Long id);
}
