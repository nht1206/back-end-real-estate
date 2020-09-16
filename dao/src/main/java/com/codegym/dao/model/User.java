package com.codegym.dao.model;

import com.codegym.dao.model.audit.DateAudit;
import com.fasterxml.jackson.annotation.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@JsonIgnoreProperties({
        "posts",
        "password",
        "accountReports"
})
public class User extends DateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "NVARCHAR(50) NOT NULL")
    private String name;

    @Column(columnDefinition = "NVARCHAR(100) NOT NULL")
    private String address;

    @Column(columnDefinition = "NVARCHAR(50) NOT NULL")
    private String phoneNumber;

    @Column(columnDefinition = "NVARCHAR(50) NOT NULL")
    private String email;

    @Column(columnDefinition = "NVARCHAR(61) NOT NULL")
    private String password;

    @Column(columnDefinition = "NVARCHAR(255)")
    private String avatar;

    @Column(columnDefinition = "TINYINT(1) default 1")
    private boolean status = true;

    @Column(columnDefinition = "TINYINT(1) default 0")
    private boolean activated;

    @JsonManagedReference
    @OneToMany(mappedBy = "user")
    private Set<Post> posts;

    @ManyToMany
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "FK_users_roles_user")),
            inverseJoinColumns = @JoinColumn(name = "role_id", foreignKey = @ForeignKey(name = "FK_users_roles_role"))
    )
    private Set<Role> roles;


    @OneToMany(mappedBy = "user", cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH})
    private Set<AccountReport> accountReports;

    public User() {
    }

    public User(String name, String email, String address, String phoneNumber, String encode) {
        this.name = name;
        this.email = email;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.password = encode;
    }



    public User(String name, String address, String phoneNumber, String email, String password, String avatar, boolean status, boolean activated) {
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.password = password;
        this.avatar = avatar;
        this.status = status;
        this.activated = activated;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public Set<Post> getPosts() {
        return posts;
    }

    public void setPosts(Set<Post> posts) {
        this.posts = posts;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Set<AccountReport> getAccountReports() {
        return accountReports;
    }

    public void addAccountReport(AccountReport accountReport) {
        if (accountReports == null) {
            accountReports = new HashSet<>();
        }
        accountReports.add(accountReport);
    }
}
