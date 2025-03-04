package com.minbak.web.host_pages;

import com.minbak.web.host_pages.dto.HostDto;
import com.minbak.web.spring_security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/host")
public class HostApiController {

    @Autowired
    private HostService hostService;
    @Autowired
    private ImageService imageService;
    @Autowired
    private RoomOptionService roomOptionService;

    @PostMapping("/register")
    public ResponseEntity<?> createRoomWithImages(@RequestBody HostDto hostDto) {
        try {
            // 📌 숙소 정보 저장 (rooms 테이블)
            Integer roomId = hostService.insertRoom(hostDto);

            // 📌 이미지 저장 (image_files 테이블)
            if (hostDto.getImageUrls() != null && !hostDto.getImageUrls().isEmpty()) {
                imageService.saveImages(roomId, hostDto.getImageUrls(), hostDto.getUserId());
            }

            // 📌 선택한 옵션 저장 (rooms_room_options 테이블)
            if (hostDto.getOptionIds() != null && !hostDto.getOptionIds().isEmpty()) {
                roomOptionService.saveRoomOptions(roomId, hostDto.getOptionIds());
            }

            return ResponseEntity.ok().body(Map.of("message", "숙소 등록 완료!", "roomId", roomId));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "숙소 등록 실패", "message", e.getMessage()));
        }
    }
}
