package com.minbak.web.file_upload;

import com.minbak.web.spring_security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
public class FileService {

    @Value("${file.upload.directory}")
    private String uploadDirectory;

    @Autowired
    FileMapper fileMapper;


    public ImageFileDto saveFile(MultipartFile file, int roomId, String type, Integer userId) throws IOException {
        String originalFilename = file.getOriginalFilename();
        String uniqueFilename = UUID.randomUUID().toString() + "_" + originalFilename;

        //실제로 파일을 저장할 위치
        Path filePath = Paths.get(uploadDirectory, uniqueFilename);
        Files.copy(file.getInputStream(), filePath);

        // 파일 정보 DB 저장
        ImageFileDto imageFile = new ImageFileDto();
        //웹에서 사진을 다운받을 경로
        imageFile.setFileUrl("/uploads/" + uniqueFilename);
        //기존 파일 이름
        imageFile.setFileName(originalFilename);
        //파일 사이즈 인트로변환
        imageFile.setFileSize((int) file.getSize());
        //rooms의 사진이라는 뜻
        imageFile.setEntityType(type); // type 타입으로 저장
        //해당 room의Id
        imageFile.setEntityId(roomId);
        //유저 아이디 추가
        imageFile.setUserId(userId);

        fileMapper.insertImageFile(imageFile);

        return imageFile;
    }

    public List<ImageFileDto> getImagesByRoomId(int roomId) {
        return fileMapper.findImagesByRoomId(roomId);
    }

    public boolean deleteFile(String fileUrl) {
        if (fileUrl == null || fileUrl.isEmpty()) {
            return false;
        }
        String cleanedUrl = fileUrl.replaceFirst("^/uploads", "");

        Path filePath = Paths.get(uploadDirectory, cleanedUrl); // 실제 저장된 경로
        try {
            fileMapper.deleteFileDataByUrl(fileUrl);
            return Files.deleteIfExists(filePath);
        } catch (IOException e) {
            throw new RuntimeException("파일 삭제 실패: " + fileUrl, e);
        }
    }

    public String findLicenseImagesUrlByHostId(int hostId){
        return fileMapper.findLicenseImagesUrlByHostId(hostId);
    }

    public int getUserIdByRoomId(int roomId){
        return fileMapper.getUserIdByRoomId(roomId);
    }
}
