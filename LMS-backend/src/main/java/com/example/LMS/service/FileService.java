package com.example.LMS.service;

import com.example.LMS.entity.FileEntity;
import com.example.LMS.entity.UserEntity;
import com.example.LMS.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class FileService {

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private UserService userService;

    @Value("${upload.dir}")
    private String uploadDir;  // Set this in your application.properties or application.yml

    @Value("${upload.folders.doc}")
    private String docFolder;

    @Value("${upload.folders.pdf}")
    private String pdfFolder;

    @Value("${upload.folders.docx}")
    private String docxFolder;

    @Value("${upload.folders.others}")
    private String othersFolder;

    public List<FileEntity> getAllFiles() {
        return fileRepository.findAll();
    }

    public FileEntity saveFile(MultipartFile file, String fileExtension, Long userId) {
        // Validate file
        if (file.isEmpty()) {
            throw new RuntimeException("File is empty");
        }

        try {
            // Save the file to the server
            String fileName = file.getOriginalFilename();
            Path filePath = Path.of(getFolderByExtension(fileExtension), fileName);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);


            UserEntity user = userService.getUserById(userId);
            if (user == null) {
                throw new IllegalArgumentException("User not found");
            }

            // Save file details to the database
            FileEntity fileEntity = new FileEntity();
            fileEntity.setFileName(fileName);
            fileEntity.setFileExtension(getFileExtension(fileName));
            fileEntity.setFileUploadTime(LocalDateTime.now());
            fileEntity.setUser(user);

            fileEntity.setCustomerId(userId);

            return fileRepository.save(fileEntity);
        } catch (IOException e) {
            throw new RuntimeException("Failed to store the file", e);
        }
    }

    private String getFileExtension(String fileName) {
        int lastDotIndex = fileName.lastIndexOf('.');
        if (lastDotIndex > 0) {
            return fileName.substring(lastDotIndex + 1);
        }
        return ""; // No extension found
    }
    private String getFolderByExtension(String fileExtension) {
        switch (fileExtension.toLowerCase()) {
            case "doc":
                return docFolder;
            case "pdf":
                return pdfFolder;
            case "docx":
                return docxFolder;
            default:
                return othersFolder;
        }
    }
}

