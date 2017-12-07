package com.example.itblog.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class StorageService {

    Logger log = LoggerFactory.getLogger(this.getClass().getName());
    private final Path UPLOADED_FOLDER = Paths.get("/Users/japanquality/Desktop/Storage");

    public void store(MultipartFile file, String filename) {
        try {
            Files.copy(file.getInputStream(), UPLOADED_FOLDER.resolve(filename));
        } catch (Exception e) {
            throw new RuntimeException("FAIL!");
        }
    }

    public Resource loadFile(String filename) {
        try {
            Path file = UPLOADED_FOLDER.resolve(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("FAIL!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("FAIL!");
        }
    }

    public void deleteAll() {
        FileSystemUtils.deleteRecursively(UPLOADED_FOLDER.toFile());
    }

    public void init() {
        try {
            Files.createDirectory(UPLOADED_FOLDER);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize storage!");
        }
    }

    public String genarateFilename(MultipartFile file) {
        String originalFilename =  file.getOriginalFilename();
        long random = System.currentTimeMillis();
        String hyphenFilename = originalFilename.replaceAll("\\s", "-");
        return random + "-" + hyphenFilename;

    }
}

