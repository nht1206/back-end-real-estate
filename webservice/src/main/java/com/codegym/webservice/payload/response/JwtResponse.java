package com.codegym.webservice.payload.response;

import com.codegym.dao.model.Role;

import java.util.Set;

public class JwtResponse {
    private String token;
    private String type = "Bearer ";
    private Long id;
    private String email;
    private String name;
    private Set<Role> roles;

    public JwtResponse(String accessToken, Long id, String email, String name, Set<Role> roles) {
        this.token = accessToken;
        this.id = id;
        this.email = email;
        this.name = name;
        this.roles = roles;
    }



    public String getAccessToken() {
        return token;
    }

    public void setAccessToken(String accessToken) {
        this.token = accessToken;
    }

    public String getTokenType() {
        return type;
    }

    public void setTokenType(String tokenType) {
        this.type = tokenType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Role> getRoles() {
        return roles;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
