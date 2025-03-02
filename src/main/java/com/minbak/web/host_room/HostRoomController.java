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
        return "host-room/host_room_list";
    }


    // 숙소 등록 폼 페이지
    @GetMapping("/add")
    public String showAddRoomForm() {
        return "host-room/host_room_form";
    }

    // 숙소 등록 처리
    @PostMapping("/add")
    public String addHostRoom(@AuthenticationPrincipal CustomUserDetails userDetails,
                              @ModelAttribute HostRoomDTO hostRoomDTO) {
        hostRoomDTO.setUserId(userDetails.getUserId()); // 로그인한 유저 ID 설정
        hostRoomService.addRoom(hostRoomDTO);
        return "redirect:/host/room/list"; // 숙소 목록 페이지로 이동
    }

    // 수정 폼 페이지 (기존 데이터 불러오기)
    @GetMapping("/edit/{roomId}")
    public String showEditRoomForm(@PathVariable("roomId") Integer roomId, Model model) {
        HostRoomDTO room = hostRoomService.getRoomById(roomId); // 기존 숙소 정보 불러오기
        model.addAttribute("room", room);
        return "host-room/host_room_edit";
    }
    @PostMapping("/edit/{roomId}")
    public String editHostRoom(@PathVariable("roomId") Integer roomId,
                               @RequestParam("name") String name,
                               @RequestParam("title") String title,
                               @RequestParam("content") String content,
                               @RequestParam("address") String address,
                               @RequestParam("price") int price,
                               @RequestParam("maxGuests") int maxGuests,
                               @RequestParam("buildingType") String buildingType) {
        HostRoomDTO hostRoomDTO = new HostRoomDTO();
        hostRoomDTO.setRoomId(roomId);
        hostRoomDTO.setName(name);
        hostRoomDTO.setTitle(title);
        hostRoomDTO.setContent(content);
        hostRoomDTO.setAddress(address);
        hostRoomDTO.setPrice(price);
        hostRoomDTO.setMaxGuests(maxGuests);
        hostRoomDTO.setBuildingType(buildingType);

        hostRoomService.updateRoom(hostRoomDTO);
        return "redirect:/host/room/list";
    }
    @GetMapping("/delete/{roomId}")
    public String deleteHostRoom(@PathVariable("roomId") int roomId){
        hostRoomService.deleteRoom(roomId);
        return "redirect:/host/room/list";
    }



}





