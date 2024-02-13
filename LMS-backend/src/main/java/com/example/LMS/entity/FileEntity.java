package com.example.LMS.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;  // Import for LocalDateTime

@Entity
@Table(name = "files")
public class FileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "file_id")  // Rename the column to file_id
    private Long id;

    @Column(nullable = false)
    private String fileName;

    @Column(nullable = false)
    private String fileExtension;

    @Column(name = "file_upload_time", nullable = false)
    private LocalDateTime fileUploadTime;  // Column for upload time

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")  // Map to user id
    private UserEntity user;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileExtension() {
        return fileExtension;
    }

    public void setFileExtension(String fileExtension) {
        this.fileExtension = fileExtension;
    }

    public LocalDateTime getFileUploadTime() {
        return fileUploadTime;
    }

    public void setFileUploadTime(LocalDateTime fileUploadTime) {
        this.fileUploadTime = fileUploadTime;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }
    public void setCustomerId(Long customerId) {
        if (this.user == null) {
            this.user = new UserEntity(); // Create a new UserEntity if not already present
        }
        this.user.setId(customerId);
    }
}
