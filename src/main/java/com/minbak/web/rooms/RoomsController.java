package com.minbak.web.rooms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class RoomsController {

    @Autowired
    private RoomsService roomsService;

//
    @GetMapping("/rooms/list")
    public String roomsList(@RequestParam(name="page", defaultValue = "1") int page,
                             @RequestParam(name="size", defaultValue = "10") int size,
                             Model model){
        RoomsPageDto roomsPage = roomsService.getRooms(page, size);
        model.addAttribute("roomsPage" , roomsPage);

        return "board/rooms/rooms_list";
    }
//  숙소 이름을 클릭 했을 때 상세보기
    @GetMapping("/rooms/{room_id}")
    public String roomsDetail(@PathVariable int room_id, Model model){
        RoomsDto room = roomsService.selectRoomById(room_id);
        model.addAttribute("room", room);
        return "board/rooms/rooms_detail";
    }
//  목록으로 돌아가기
    @GetMapping("/board/rooms/rooms_list")
    public String returnList(@RequestParam(name = "page", defaultValue = "1") int page,
                            @RequestParam(name = "size", defaultValue = "10") int size,
                            Model model) {
        RoomsPageDto roomsPage = roomsService.getRooms(page, size);
        model.addAttribute("roomsPage", roomsPage);
        return "board/rooms/rooms_list";
}
    // 상세보기 페이지
    @GetMapping("/rooms/edit/{roomId}")
    public String updateRoom(@PathVariable("roomId") int roomId, Model model){
        RoomsDto room = roomsService.selectRoomById(roomId);
        model.addAttribute("room", room);
        return "board/rooms/rooms_edit";
    }
    // 업데이트 클릭시 상세보기로 리다이렉팅
    @PostMapping("/rooms/update")
    public String updateRoom(RoomsDto room){
        roomsService.updateRoom(room);
        return "redirect:/admin/rooms/" + room.getRoomId();
    }
    // 삭제 버튼 클릭시 삭제
    @PostMapping("/rooms/delete/{roomId}")
    public String deleteRoom(@PathVariable int roomId){
        roomsService.deleteRoom(roomId);
        return "redirect:/admin/board/rooms/rooms_list";
    }

}
