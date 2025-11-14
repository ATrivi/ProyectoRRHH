package com.example.proyectoRecursosHumanos.dto;

import com.example.proyectoRecursosHumanos.model.User;

public class UserResponse{

    private int id;
    private String userName;
    private String role;
    private String email;

    public UserResponse() {}

    public UserResponse(int id, String userName, String role, String email) {
        this.id = id;
        this.userName = userName;
        this.role = role;
        this.email = email;
    }

    public UserResponse(User user) {
        this.id = user.getId();
        this.userName = user.getUserName();
        this.role = user.getRole();
        this.email = user.getEmail();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

