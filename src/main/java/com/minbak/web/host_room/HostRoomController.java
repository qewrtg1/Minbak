package com.minbak.web.host_room;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("user/host/room") // 변경된 부분
@RequiredArgsConstructor
public class HostRoomController {

    private final HostRoomService hostRoomService; // 서비스 주입

//호스트 로그인 확인
    @GetMapping("/check")
    public ResponseEntity<?> checkIfHost(HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "로그인이 필요합니다."));
        }
        boolean isHost = hostRoomService.isHost(userId);
        return ResponseEntity.ok(Map.of("isHost", isHost));
    }

//숙소 목록 조회
    @GetMapping("/list")
    public String showHostRooms(HttpSession session, Model model) {
        Integer userId = (Integer) session.getAttribute("userId");
        String role = (String) session.getAttribute("userRole"); // 세션에서 역할(Role) 확인

        // 🚨 로그인하지 않은 경우 로그인 페이지로 이동
        if (userId == null || role == null) {
            return "redirect:/admin/login";
        }

        // 🚨 호스트 또는 관리자가 아니라면 접근 제한
        if (!"ROLE_HOST".equals(role) && !"ROLE_ADMIN".equals(role)) {
            return "redirect:/error/403"; // 접근 금지 페이지
        }

        // 🚀 숙소 목록 불러오기
        List<HostRoomDTO> hostRooms = hostRoomService.getRoomsByHost(userId);
        model.addAttribute("rooms", hostRooms);

        return "host-room/host_room_list";
    }
    @GetMapping("/debug/session")
    public ResponseEntity<?> checkSession(HttpSession session) {
        try {
            Integer userId = (Integer) session.getAttribute("userId");
            String role = (String) session.getAttribute("userRole");

            // 🚨 디버깅 메시지 추가
            System.out.println("세션 체크: userId=" + userId + ", userRole=" + role);

            return ResponseEntity.ok(Map.of(
                    "userId", userId != null ? userId : "세션 없음",
                    "userRole", role != null ? role : "세션 없음"
            ));
        } catch (Exception e) {
            e.printStackTrace(); // 콘솔에 오류 출력
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "error", "세션 조회 중 오류 발생",
                    "message", e.getMessage()
            ));
        }
    }


}
