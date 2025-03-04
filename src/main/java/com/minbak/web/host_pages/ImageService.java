package com.minbak.web.host_pages;

import com.minbak.web.host_pages.dto.CreateImageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageService {
    private final CreateImageMapper createImageMapper;

    @Transactional
    public void saveImages(Integer roomId, List<String> images, Integer userId){
        for (String imageUrl : images){
            String fileName = imageUrl.substring(imageUrl.lastIndexOf("/")+1);
            CreateImageDto createImageDto = new CreateImageDto();
            createImageMapper.insertImage(createImageDto);
        }
    }

    // 저장된 이미지를 불러옴
    public List<CreateImageDto> getImageByRoomId(Integer roomId){
        return createImageMapper.getImagesByRoomId(roomId);
    }

}
