package com.minbak.web.user_YH.signup;

import com.minbak.web.users.UserDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class SignupApiController {

    @PostMapping("/signup")
    public ResponseEntity<Map<String, Object>> signUp(@RequestBody UserDto userDto) {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "회원가입 성공!");

        return ResponseEntity.ok(response); // JSON 응답
    }
}
