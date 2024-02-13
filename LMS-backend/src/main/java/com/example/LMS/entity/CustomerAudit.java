package com.example.LMS.entity;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name = "customer_audit")
public class CustomerAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_audit_id")
    private Long customerAuditId;

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private UserEntity user;

    @Column(name = "last_login_time")
    private Timestamp lastLoginTime;

    @Column(name = "last_updated")
    private Timestamp lastUpdated;

    @Column(name = "last_deleted")
    private Timestamp lastDeleted;

    // Constructors, getters, setters, etc.

    // Default constructor
    public CustomerAudit() {
    }

    // Parameterized constructor for creation
    public CustomerAudit(UserEntity user, Timestamp lastLoginTime) {
        this.user = user;
        this.lastLoginTime = lastLoginTime;
    }

    // Parameterized constructor for updates
    public CustomerAudit(UserEntity user, Timestamp lastUpdated, Timestamp lastDeleted) {
        this.user = user;
        this.lastUpdated = lastUpdated;
        this.lastDeleted = lastDeleted;
    }

    // Getters and setters

    public Long getCustomerAuditId() {
        return customerAuditId;
    }

    public void setCustomerAuditId(Long customerAuditId) {
        this.customerAuditId = customerAuditId;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public Timestamp getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Timestamp lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public Timestamp getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Timestamp lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public Timestamp getLastDeleted() {
        return lastDeleted;
    }

    public void setLastDeleted(Timestamp lastDeleted) {
        this.lastDeleted = lastDeleted;
    }

    // Additional methods or custom logic if needed
}
