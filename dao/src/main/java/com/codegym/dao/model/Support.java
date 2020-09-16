package com.codegym.dao.model;

import com.codegym.dao.model.audit.DateAudit;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "support")
public class Support extends DateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "NVARCHAR(50) NOT NULL")
    private String name;
    @Column(columnDefinition = "NVARCHAR(50) NOT NULL")
    private String email;
    @Column(columnDefinition = "NVARCHAR(50) NOT NULL")
    private String phoneNumber;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "reason_id", foreignKey = @ForeignKey (name = "FK_support_reason"))
    private Reason reason;
    @Column(columnDefinition = "TEXT NOT NULL")
    private String content;

    @Column(columnDefinition = "TINYINT(1) default 1")
    private boolean status = true;
    @Column(columnDefinition = "TINYINT(1) default 0")
    private boolean checked = false;

    public Support() {
    }

    public Support(String name, String email, String phoneNumber, Reason reason, String content, boolean status, boolean checked) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.reason = reason;
        this.content = content;
        this.status = status;
        this.checked = checked;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Reason getReason() {
        return reason;
    }

    public void setReason(Reason reason) {
        this.reason = reason;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
