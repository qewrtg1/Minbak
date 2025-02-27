package com.minbak.web.dash_board;

import com.minbak.web.spring_security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/admin/api")
public class DashBoardApiController {

    @Autowired
    DashBoardService dashBoardService;

    @PostMapping("/send")
    @ResponseBody
    public ResponseEntity<Map<String, String>> sendMessage(
            @RequestBody Map<String, String> payload,
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        Integer adminId = Integer.parseInt(payload.get("adminId"));
        String message = payload.get("message");

        if (message == null || message.trim().isEmpty()) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "유효한 메시지를 입력하세요.");
            return ResponseEntity.badRequest().body(errorResponse);
        }

        System.out.println(userDetails);
        System.out.println(userDetails.getUserId());

        dashBoardService.sendMessage(userDetails.getUserId(),adminId,message);

        // 간단한 JSON 응답
        Map<String, String> response = new HashMap<>();
        response.put("message", "메시지가 전송되었습니다.");

        return ResponseEntity.ok(response);
    }



}
