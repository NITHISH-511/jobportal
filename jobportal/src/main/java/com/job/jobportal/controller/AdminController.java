package com.job.jobportal.controller;

import java.util.*;

//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.job.jobportal.entity.Admin;
import com.job.jobportal.service.AdminService;

@RestController
@RequestMapping("/api")
public class AdminController {

    private final AdminService adminService;
    private final ObjectMapper objectMapper; // Fix: Add ObjectMapper

    
    public AdminController(AdminService adminService, ObjectMapper objectMapper) {
        this.adminService = adminService;
        this.objectMapper = objectMapper;
    }

    @GetMapping("/admins")
    public ResponseEntity<List<Admin>> getAllAdmins() { // Fix: Typo in method name
        List<Admin> admins = adminService.getAllAdmins();
        return new ResponseEntity<>(admins, HttpStatus.OK);
    }

    @GetMapping("/admins/{id}")
    public ResponseEntity<Admin> getAdminById(@PathVariable Long id) {
        Optional<Admin> admin = adminService.getAdminById(id);
        return admin.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/admins/by-usr/{username}")
    public ResponseEntity<Admin> getAdminByUsername(@PathVariable String username) {
        Optional<Admin> admin = adminService.getAdminByUsername(username);
        return admin.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/admins//list")
    public ResponseEntity<Map<String, Object>> getAdmins(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {
        Page<Admin> admins = adminService.getAdminsPaginated(page, size, sortBy, sortDir);

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("content", admins.getContent());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // -------------------------------POST------------------------------------------------------------------------

    @PostMapping("/admins")
    public ResponseEntity<?> createAdmin(@RequestBody Object requestBody) {
        try {

            String json = objectMapper.writeValueAsString(requestBody);

            if (json.trim().startsWith("[")) {
                List<Admin> admins = objectMapper.readValue(json,
                        objectMapper.getTypeFactory().constructCollectionType(List.class, Admin.class));
                List<Admin> savedAdmins = adminService.createAdmins(admins);
                return new ResponseEntity<>(savedAdmins, HttpStatus.CREATED);
            } else {
                Admin admin = objectMapper.readValue(json, Admin.class);
                Admin savedAdmin = adminService.createAdmin(admin);
                return new ResponseEntity<>(savedAdmin, HttpStatus.CREATED);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Error processing request: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // ---------------------------------MODIFY----------------------------------------------------------------------

    @PutMapping("/admins/{id}")
    public ResponseEntity<Admin> updateAdmin(@PathVariable Long id, @RequestBody Admin admin) {
        try {
            Admin updatedAdmin = adminService.updateAdmin(id, admin);
            return new ResponseEntity<>(updatedAdmin, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // ---------------------------------DEL----------------------------------------------------------------------

    @DeleteMapping("/admins/{id}")
    public ResponseEntity<Void> deleteAdmin(@PathVariable Long id) {
        try {
            adminService.deleteAdmin(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
