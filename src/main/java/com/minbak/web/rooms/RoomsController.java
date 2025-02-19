package com.minbak.web.rooms;

import com.minbak.web.rooms.dto.RoomsDto;
import com.minbak.web.rooms.dto.RoomsPageDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class RoomsController {

    @Autowired
    private RoomsService roomsService;

// 리스트
    @GetMapping("/rooms")
    public String roomsList(@RequestParam(name="page", defaultValue = "1") int page,
                             @RequestParam(name="size", defaultValue = "10") int size,
                             Model model){
        RoomsPageDto roomsPage = roomsService.getRooms(page, size);
        model.addAttribute("roomsPage" , roomsPage);

        return "rooms/rooms_list";
    }
//  숙소 이름을 클릭 했을 때 상세보기
    @GetMapping("/rooms/detail/{roomId}")
    public String roomsDetail(@PathVariable int roomId, Model model){
        // 해당 데이터가 없을 때 예외처리
        RoomsDto room = roomsService.getRoomList(roomId);
        if(room == null){
            throw new RoomException("데이터를 찾을 수 없습니다.");
        }
        model.addAttribute("room", room);

        return "rooms/rooms_detail";
    }

    @GetMapping("/rooms/rooms_detail/{roomId}")
    public String getRoomDetail(@PathVariable int roomId, Model model) {
        RoomsDto room = roomsService.getRoomById(roomId);  // 서비스에서 해당 roomId로 방 정보를 가져옴
        model.addAttribute("room", room);  // 모델에 방 정보를 담아서 뷰로 전달
        return "rooms/rooms_detail";  // rooms/detail.html 페이지로 이동
    }
//  목록으로 돌아가기
//    @GetMapping("/rooms/rooms_list")
//    public String returnList(@RequestParam(name = "page", defaultValue = "1") int page,
//                            @RequestParam(name = "size", defaultValue = "10") int size,
//                            Model model) {
//        RoomsPageDto roomsPage = roomsService.getRooms(page, size);
//        model.addAttribute("roomsPage", roomsPage);
//        return "rooms/rooms_list";
//}
    // 수정하기 페이지
    @GetMapping("/rooms/edit/{roomId}")
    public String updateRoom(@PathVariable int roomId, Model model){
        RoomsDto room = roomsService.getRoomById(roomId);
        if (room == null) {
            throw new RoomException("데이터를 찾을 수 없습니다.");
        }
        model.addAttribute("room", room);
        return "rooms/rooms_edit";
    }
    // 업데이트 클릭시 상세보기로 리다이렉팅
    @PostMapping("/rooms/update/{roomId}")
    public String updateRoom(@PathVariable int roomId, RoomsDto roomsDto){
        roomsDto.setRoomId(roomId);
        roomsService.updateRoom(roomsDto);
        return "redirect:/admin/rooms/rooms_detail/" + roomId;
    }

    // 삭제 버튼 클릭시 삭제
    @PostMapping("/rooms/delete/{roomId}")
    public String deleteRoom(@PathVariable int roomId){
        RoomsDto room = roomsService.getRoomById(roomId); // 삭제할 방 조회
        if (room == null) {
            throw new RoomException("데이터가 이미 삭제 되었거나 찾을 수 없습니다.");
        }
        roomsService.deleteRoom(roomId); // 해당 방 삭제
        return "redirect:/admin/rooms/rooms_list"; // 삭제 후 목록 페이지로 리다이렉트
    }

}
