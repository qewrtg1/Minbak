package com.minbak.web.host_room;

import com.minbak.web.spring_security.CustomUserDetails;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("host/room") // 변경된 부분
@RequiredArgsConstructor
public class HostRoomController {

    private final HostRoomService hostRoomService; // 서비스 주입



    //숙소 목록 조회
    @GetMapping("/list")
    public String showHostRooms(@AuthenticationPrincipal CustomUserDetails userDetails, Model model) {

        // 🚀 숙소 목록 불러오기
        List<HostRoomDTO> hostRooms = hostRoomService.getRoomsByHost(userDetails.getUserId());
        //userDetails.getUserId() : 로그인한 사람의 user id값을 불러옴.
        model.addAttribute("rooms", hostRooms);
        return "host-pages/host_room_list";
    }


}





