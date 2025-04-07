package com.job.jobportal.entity;

import jakarta.persistence.*;

@Entity

public class Admin {

    @Id
    private Long id;

    @Column(nullable = false) 
    private String username;

    @Column(nullable = false)
    private String password;


    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}