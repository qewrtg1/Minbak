package com.minbak.web.file_upload;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileService {

    @Value("${file.upload.directory}")
    private String uploadDirectory;

    @Autowired
    FileMapper fileUploadMapper;


    public ImageFileDto saveFile(MultipartFile file, int roomId) throws IOException {
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
        imageFile.setEntityType("rooms"); // rooms 타입으로 저장
        //해당 room의Id
        imageFile.setEntityId(roomId);
        // TODO 유저 아이디 추가
        fileUploadMapper.insertImageFile(imageFile);

        return imageFile;
    }
}
