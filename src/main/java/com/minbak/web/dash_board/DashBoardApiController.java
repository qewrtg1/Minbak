package com.minbak.web.dash_board;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/admin/api")
public class DashBoardApiController {

    @PostMapping("/send-message")
    @ResponseBody
    public ResponseEntity<?> sendMessage(@RequestBody Map<String, String> payload) {
        String adminId = payload.get("adminId");
        String message = payload.get("message");

        if (adminId == null || message == null || message.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("유효한 메시지를 입력하세요.");
        }

        // 메시지 저장 로직 로그인 기능 머지하고 짜야함



        System.out.println("관리자 ID: " + adminId + "에게 메시지 전송: " + message);

        return ResponseEntity.ok().body("메시지가 전송되었습니다.");
    }



}
