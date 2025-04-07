package com.job.jobportal.service;

import com.job.jobportal.entity.Company;
import com.job.jobportal.repository.CompanyRepository;

import org.springframework.stereotype.Service;



import java.util.List;
import java.util.Optional;

@Service
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;

    public CompanyServiceImpl(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @Override
    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    @Override
    public Company getCompanyById(Long id) {
        return companyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Company not found with ID: " + id));
    }

    @Override
    public Company saveCompany(Company company) {
        return companyRepository.save(company);
    }

    @Override
    public Company updateCompany(Long id, Company company) {
        Optional<Company> existingCompany = companyRepository.findById(id);
        if (existingCompany.isPresent()) {
            Company updatedCompany = existingCompany.get();
            updatedCompany.setName(company.getName());
            updatedCompany.setLocation(company.getLocation());
            updatedCompany.setIndustry(company.getIndustry());
            updatedCompany.setDescription(company.getDescription());
            return companyRepository.save(updatedCompany);
        } else {
            throw new RuntimeException("Company not found with ID: " + id);
        }
    }

    @Override
    public void deleteCompany(Long id) {
        companyRepository.deleteById(id);
    }
}
