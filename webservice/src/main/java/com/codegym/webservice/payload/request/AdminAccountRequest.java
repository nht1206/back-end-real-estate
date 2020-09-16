package com.codegym.webservice.payload.request;

import com.codegym.dao.model.Role;

public class AdminAccountRequest {
    private String email;
    private String name;
    private Role role;

    public AdminAccountRequest() {
    }

    public AdminAccountRequest(String email, String name, Role role) {
        this.email = email;
        this.name = name;
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
