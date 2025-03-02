package com.minbak.web.file_upload;

import com.minbak.web.file_upload.ImageFileDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FileMapper {

    void insertImageFile(ImageFileDto imageFile);
    List<ImageFileDto> findImagesByRoomId(int roomId);
    String findLicenseImagesUrlByHostId(int hostId);

}
