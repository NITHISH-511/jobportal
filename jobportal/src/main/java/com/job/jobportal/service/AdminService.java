package com.job.jobportal.service;

import com.job.jobportal.entity.Admin;
import org.springframework.data.domain.Page;
import java.util.*;

public interface AdminService {
    List<Admin> getAllAdmins();

    Admin createAdmin(Admin admin);
    List<Admin> createAdmins(List<Admin> admins);

    Optional<Admin> getAdminByUsername(String username);

    Optional<Admin> getAdminById(Long id);
    
    Admin updateAdmin(Long id, Admin admin);

    void deleteAdmin(Long id);

    Page<Admin> getAdminsPaginated(int page, int size, String sortBy, String sortDir);

}