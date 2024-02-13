package com.example.LMS.repository;


import com.example.LMS.entity.CustomerAudit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerAuditRepository extends JpaRepository<CustomerAudit, Long> {
}
