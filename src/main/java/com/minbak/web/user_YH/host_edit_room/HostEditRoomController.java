package com.minbak.web.user_YH.host_edit_room;

import com.minbak.web.spring_security.CustomUserDetails;
import com.minbak.web.user_YH.host_edit_room.dto.HostRoomEditDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class HostEditRoomController {

    @Autowired
    HostEditRoomService hostEditRoomService;

    @GetMapping("/host/room/edit/{roomId}")
    public String hostRoomEditPage(@PathVariable("roomId") int roomId,
                                   @AuthenticationPrincipal CustomUserDetails userDetails,
                                   Model model) {

        //숙소를 등록한 유저인지 확인
//        if(hostEditRoomService.getHostUserIdByRoomId(roomId) != userDetails.getUserId()){
//            return "/";
//        }

        // 숙소 정보 가져오기
        HostRoomEditDto roomData = hostEditRoomService.getHostRoomData(roomId);

        // 모델에 데이터 추가
        model.addAttribute("room", roomData);

        return "/host-pages/edit-room";
    }
}
