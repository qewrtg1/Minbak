package com.minbak.web.config;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Configuration
public class FileUploadConfig {
    @Value("${file.upload.directory:/tmp/uploads}")
    private String uploadDirectory;

    @PostConstruct
    public void init() {
        try {
            Files.createDirectories(Paths.get(uploadDirectory));
        } catch (IOException e) {
            throw new RuntimeException("업로드 디렉토리를 만들 수 없습니다.");
        }
    }
}
