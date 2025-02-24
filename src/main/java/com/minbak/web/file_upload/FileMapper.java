package com.minbak.web.file_upload;

import com.minbak.web.file_upload.ImageFileDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FileMapper {

    void insertImageFile(ImageFileDto imageFile);
}
