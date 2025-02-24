package com.minbak.web.file_upload;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class ApiFileController {

    @Value("${file.upload.directory}")
    private String uploadDirectory;

    @Autowired
    FileService fileService;

    @PostMapping("/upload-multiple")
    public ResponseEntity<?> uploadMultipleFiles(
            @RequestParam("roomId") int roomId,
            @RequestParam("files") MultipartFile[] files) {

        if (files.length == 0) {
            return ResponseEntity.badRequest().body("업로드할 파일이 없습니다.");
        }

        List<ImageFileDto> uploadedFiles = new ArrayList<>();

        for (MultipartFile file : files) {
            try {
                ImageFileDto imageFile = fileService.saveFile(file, roomId);
                uploadedFiles.add(imageFile);
            } catch (IOException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("파일 저장 중 오류 발생: " + e.getMessage());
            }
        }

        return ResponseEntity.ok(uploadedFiles);
    }

    @DeleteMapping("/delete/{filename}")
    public ResponseEntity<String> deleteFile(@PathVariable String filename) {
        try {
            Path filePath = Paths.get(uploadDirectory).resolve(filename);
            Files.deleteIfExists(filePath);
            return ResponseEntity.ok("파일 삭제 완료: " + filename);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("파일 삭제 실패: " + e.getMessage());
        }
    }


}
