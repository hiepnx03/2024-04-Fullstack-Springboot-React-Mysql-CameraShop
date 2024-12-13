package com.example.laptop_be.service;

import com.example.laptop_be.converter.Converter;
import com.example.laptop_be.dto.ImageDTO;
import com.example.laptop_be.entity.Image;
import com.example.laptop_be.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ImageService {

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private Converter converter;

    private final Path rootLocation = Paths.get("upload-dir");

    public ImageService() {
        try {
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize storage", e);
        }
    }

    public ImageDTO createImage(ImageDTO imageDTO) {
        Image image = converter.convertToEntity(imageDTO);
        Image savedImage = imageRepository.save(image);
        return converter.convertToDto(savedImage);
    }

    public ImageDTO uploadImage(MultipartFile file) {
        try {
            if (!Files.exists(rootLocation)) {
                Files.createDirectories(rootLocation);
            }

            String filename = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
            Path filePath = this.rootLocation.resolve(filename);
            Files.copy(file.getInputStream(), filePath);
            Image image = new Image();
            image.setNameImage(file.getOriginalFilename());
            image.setUrlImage(filePath.toString()); // Store the path as URL
            Image savedImage = imageRepository.save(image);
            return converter.convertToDto(savedImage);
        } catch (IOException e) {
            throw new RuntimeException("Failed to store file " + file.getOriginalFilename(), e);
        }
    }

    public ImageDTO getImageById(int id) {
        Image image = imageRepository.findById(id).orElse(null);
        return converter.convertToDto(image);
    }

    public List<ImageDTO> getAllImages() {
        List<Image> images = imageRepository.findAll();
        return images.stream().map(converter::convertToDto).collect(Collectors.toList());
    }

    public ImageDTO updateImage(int id, ImageDTO imageDTO) {
        Image image = converter.convertToEntity(imageDTO);
        image.setIdImage(id);
        Image updatedImage = imageRepository.save(image);
        return converter.convertToDto(updatedImage);
    }

    public void deleteImage(int id) {
        imageRepository.deleteById(id);
    }
}
