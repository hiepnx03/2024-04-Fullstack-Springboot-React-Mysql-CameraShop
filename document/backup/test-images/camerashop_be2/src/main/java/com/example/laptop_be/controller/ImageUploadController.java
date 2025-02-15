package com.example.laptop_be.controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


@RestController
@RequestMapping("/api/images")
public class ImageUploadController {

    private static final String UPLOAD_DIR = "uploads/";

    @PostMapping("/upload")
    public String uploadImage(@RequestParam("file") MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            return "Please select a file to upload.";
        }

        // Ensure the upload directory exists
        File uploadDir = new File(UPLOAD_DIR);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        byte[] bytes = file.getBytes();
        Path path = Paths.get(UPLOAD_DIR + file.getOriginalFilename());
        Files.write(path, bytes);

        // Return the relative path to the uploaded file
        return "/api/images/files/" + file.getOriginalFilename();
    }

    @GetMapping("/files/{filename}")
    @ResponseBody
    public byte[] getImage(@PathVariable String filename) throws IOException {
        Path path = Paths.get(UPLOAD_DIR + filename);
        return Files.readAllBytes(path);
    }

    @DeleteMapping("/files/{filename}")
    public String deleteImage(@PathVariable String filename) throws IOException {
        Path path = Paths.get(UPLOAD_DIR + filename);
        Files.deleteIfExists(path);
        return "Image deleted successfully.";
    }
}