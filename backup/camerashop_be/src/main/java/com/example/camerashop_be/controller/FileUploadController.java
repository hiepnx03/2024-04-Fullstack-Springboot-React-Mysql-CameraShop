package com.example.camerashop_be.controller;

import com.example.camerashop_be.entity.ResponseObject;
import com.example.camerashop_be.service.IStorageService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Controller
@RequestMapping("/api/file-upload")
public class FileUploadController {
    private final IStorageService storageService;

    @PostMapping("")
    public ResponseEntity<ResponseObject> uploadFile(@RequestParam("file") MultipartFile[] file) {
        List<String> listImage = new ArrayList<>();
        try {
            for (MultipartFile multipartFile : file) {
                String generatedFileName = storageService.storeFile(multipartFile);
                listImage.add(generatedFileName);
            }
            return ResponseEntity.ok().body(new ResponseObject("ok", "Upload image successful!", listImage));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseObject("failed", e.getMessage(), ""));
        }
    }

    @GetMapping("/files/{fileName:.+}")
    public ResponseEntity<byte[]> readDetailFile(@PathVariable String fileName) {
        try {
            byte[] bytes = storageService.readFileContent(fileName);
            return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG)
                    .body(bytes);
        } catch (Exception e) {
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("")
    public ResponseEntity<List<String>> getUploadedFiles() {
        try {
            List<String> urls = storageService.loadAll()
                    .map(path -> {
                        //convert fileName to url (send request "readDetailFile")
                        return MvcUriComponentsBuilder.fromMethodName(FileUploadController.class,
                                "readDetailFile", path.getFileName().toString()).build().toUri().toString();
                    }).collect(Collectors.toList());
            return new ResponseEntity<>(urls, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.noContent().build();
        }
    }

    @PostMapping("/multi")
    public ResponseEntity<List<String>> uploadFile(@RequestParam("files") List<MultipartFile> files) {
        try {
            List<String> filenames = new ArrayList<>();
            for (MultipartFile file : files) {
                String generatedFileName = storageService.storeFile(file);
                filenames.add(generatedFileName);
            }
            return new ResponseEntity<>(filenames, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.NOT_IMPLEMENTED);
        }
    }

    @DeleteMapping()
    public ResponseEntity<Boolean> uploadFile(@RequestParam("file") String filename) {
        try {
            boolean result = storageService.deleteByFilename(filename);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.NOT_IMPLEMENTED);
        }
    }
}