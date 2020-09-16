package com.codegym.dao.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Table
@Entity(name = "account_report")
public class AccountReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(columnDefinition = "TEXT NOT NULL")
    @NotBlank(message = "Lý do không được để trống.")
    private String reason;

    @Column(columnDefinition = "TINYINT(1) default 1")
    private boolean status = true;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "FK_account_report_user"))
    private User user;

    public AccountReport() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
