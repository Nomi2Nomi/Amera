package com.example.Amera.controller;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.nio.file.*;
import java.util.UUID;

@RestController
@RequestMapping("/api/images")
public class ImageController {

    private static final String UPLOAD_DIR = "/opt/uploads/";

    // Загрузка изображения
    @PostMapping("/upload")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            if (file.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No file uploaded");
            }

            // Генерация уникального имени файла
            String fileName = UUID.randomUUID().toString() + getFileExtension(file);

            // Сохранение файла
            Path path = Paths.get(UPLOAD_DIR + fileName);
            Files.write(path, file.getBytes(), StandardOpenOption.CREATE);

            // Возвращаем URL изображения
            String imageUrl = "/api/images/" + fileName;
            return ResponseEntity.ok(imageUrl);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload file: " + e.getMessage());
        }
    }

    // Получение изображения
    @GetMapping("/{fileName}")
    public ResponseEntity<Resource> getImage(@PathVariable String fileName) {
        try {
            Path path = Paths.get(UPLOAD_DIR + fileName);
            Resource resource = new FileSystemResource(path); // Используем FileSystemResource для проверки существования файла

            // Проверка существования файла
            if (resource.exists()) {
                String contentType = Files.probeContentType(path);
                return ResponseEntity.ok()
                        .contentType(contentType != null ? MediaType.parseMediaType(contentType) : MediaType.APPLICATION_OCTET_STREAM)
                        .body(resource);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Получаем расширение файла
    private String getFileExtension(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        if (originalFilename != null && originalFilename.contains(".")) {
            return originalFilename.substring(originalFilename.lastIndexOf("."));
        }
        return ".jpg";  // По умолчанию сохраняем как .jpg
    }
}
