package com.example.LMS.controller;

import com.example.LMS.entity.FileEntity;
import com.example.LMS.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/files")
@CrossOrigin(origins = "http://localhost:4200")
public class FileController {

    @Autowired
    private FileService fileService;

    @GetMapping
    public List<FileEntity> getAllFiles() {
        return fileService.getAllFiles();
    }

    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("userId") Long userId) {
        try {
            // Determine file extension
            String fileExtension = getFileExtension(file.getOriginalFilename());

            // Save the file and its details
            FileEntity savedFile = fileService.saveFile(file,fileExtension, userId);

            return new ResponseEntity<>(savedFile, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to upload the file: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Helper method to extract file extension
    private String getFileExtension(String fileName) {
        int lastDotIndex = fileName.lastIndexOf(".");
        if (lastDotIndex != -1) {
            return fileName.substring(lastDotIndex + 1);
        }
        return "";  // Default to empty string if no extension found
    }
}
