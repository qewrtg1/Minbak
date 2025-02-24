package com.minbak.web.file_upload;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/admin/file")
public class FileController {
    @Value("${file.upload.directory}")
    private String uploadDirectory;

    @GetMapping("/fileform")
    public String fileForm() {
        return "item/file-form";
    }

    @PostMapping("/upload")
    public ResponseEntity<String> upload(@RequestParam("file") MultipartFile file) {

        try {
            String originalFilename = file.getOriginalFilename();
            String uniqueFilename = UUID.randomUUID().toString() + "_" + originalFilename;

            Path filePath = Paths.get(uploadDirectory, uniqueFilename);
            Files.copy(file.getInputStream(),filePath, StandardCopyOption.REPLACE_EXISTING);

            return ResponseEntity.ok("파일이 업로드 성공 :" + uniqueFilename);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("예외 발생" + e.getMessage());
        }
    }

    @PostMapping("/upload-multiple")
    public ResponseEntity<String> uploadMultiple(@RequestParam("files") MultipartFile[] files) {
        List<String> uploadedFiles = new ArrayList<>();

        for(MultipartFile file : files) {
            try {
                String originalFilename = file.getOriginalFilename();
                String uniqueFilename = UUID.randomUUID().toString() + "_" + originalFilename;

                Path filePath = Paths.get(uploadDirectory, uniqueFilename);
                Files.copy(file.getInputStream(),filePath, StandardCopyOption.REPLACE_EXISTING);

                uploadedFiles.add(uniqueFilename);
            } catch (IOException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("예외 발생" + e.getMessage());
            }
        }

        return ResponseEntity.ok("파일이 업로드 성공 :" + uploadedFiles);
    }

    @GetMapping("/download/{filename}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String filename) {
        try {
            Path filePath = Paths.get(uploadDirectory).resolve(filename);
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists() || resource.isReadable()) {
                return ResponseEntity.ok()
                        // 다운로드 처리
                        .header(HttpHeaders.CONTENT_DISPOSITION,
                                "attachment; attachmentfilename=\"" + resource.getFilename() + "\"")
                        // 웹 브라우저에서 바로보기
//                        .header(HttpHeaders.CONTENT_DISPOSITION,
//                                "inline; filename=\"" + resource.getFilename() + "\"")
//                        .contentType(MediaType.IMAGE_JPEG)
                        .body(resource);
            } else {
                throw new RuntimeException("파일을 읽을 수 없습니다.");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }
}
