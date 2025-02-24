package com.minbak.web.file_upload;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ImageFileDto {
    private int imageId;
    private String fileUrl;
    private String fileName;
    private int fileSize;
    private String entityType;
    private int entityId;
}
