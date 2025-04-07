package com.job.jobportal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import com.job.jobportal.entity.Admin;
import com.job.jobportal.repository.AdminRepository;
import org.springframework.data.domain.*;
import java.util.*;


@Service
public class AdminServiceImpl implements AdminService {

    

    
    public AdminServiceImpl(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }
   
    @Autowired
    private AdminRepository adminRepository;

    

    @Override
    public List<Admin> getAllAdmins() {
        return adminRepository.findAll();
    }

    @Transactional
    @Override
    public Admin createAdmin(Admin admin) {
        return adminRepository.save(admin);
    }
    
    @Override
    public List<Admin> createAdmins(List<Admin> admins) {
        return adminRepository.saveAll(admins);
    }

    @Override
    public Optional<Admin> getAdminById(Long id) {
        return adminRepository.findById(id);  
    }

    @Override
    public Optional<Admin> getAdminByUsername(String username) {
        return adminRepository.findByUsername(username);
    }

    @Override
    public Admin updateAdmin(Long id, Admin admin) {
        if (adminRepository.existsById(id)) {
            admin.setId(id);  // Ensure the ID is set before updating
            return adminRepository.save(admin);
        } else {
            throw new RuntimeException("Admin not found");
        }
    }

    @Override
    public void deleteAdmin(Long id) {
        if (adminRepository.existsById(id)) {
            adminRepository.deleteById(id);
        } else {
            throw new RuntimeException("Admin not found");
        }
    }

     @Override
    public Page<Admin> getAdminsPaginated(int page, int size, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return adminRepository.findAll(pageable);
    }
    
}