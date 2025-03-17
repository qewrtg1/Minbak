package com.minbak.web.user_YH.host_edit_room;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/host/api/host-edit-room")
public class HostEditRoomApiController {

    private final HostEditRoomService hostEditRoomService;

    public HostEditRoomApiController(HostEditRoomService hostEditRoomService) {
        this.hostEditRoomService = hostEditRoomService;
    }

    // 숙소 활성화 상태 변경 API
    @PostMapping("/toggle-active")
    public ResponseEntity<?> toggleRoomActive(@RequestBody Map<String, Object> requestData) {
        if (!requestData.containsKey("roomId") || !requestData.containsKey("isActive")) {
            return ResponseEntity.badRequest().body("roomId와 isActive 필드가 필요합니다.");
        }


        int roomId = (Integer) requestData.get("roomId");

        // todo 호스트가 검증됐는지 확인 후 처리코드 추가
        if(!hostEditRoomService.getHostIsVerifiedByRoomId(roomId)){
            return ResponseEntity.ok(Map.of("message", "영업검증을 먼저 진행하여 주십시오.","success", false));
        }

        boolean isActive = (Boolean) requestData.get("isActive");

        hostEditRoomService.toggleRoomActiveStatus(roomId, isActive);

        return ResponseEntity.ok(Map.of("message", "숙소 상태가 변경되었습니다.", "success", true));
    }

    // 특정 숙소의 활성화 상태 조회 API
    @GetMapping("/{roomId}/active-status")
    public ResponseEntity<?> getRoomActiveStatus(@PathVariable int roomId) {
        boolean isActive = hostEditRoomService.getRoomActiveStatus(roomId);
        return ResponseEntity.ok(Map.of("roomId", roomId, "isActive", isActive));
    }
}
