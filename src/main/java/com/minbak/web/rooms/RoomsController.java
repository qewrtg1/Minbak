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
    @GetMapping
    public String roomsList(@RequestParam(name="page", defaultValue = "1") int page,
                             @RequestParam(name="size", defaultValue = "10") int size,
                             Model model){
        RoomsPageDto roomsPage = roomsService.getRooms(page, size);
        model.addAttribute("roomsPage" , roomsPage);

        return "board/rooms/rooms_list";
    }
//  숙소 이름을 클릭 했을 때 상세보기
    @GetMapping("/{room_id}")
    public String roomsDetail(@PathVariable int room_id, Model model){
        System.out.println("room_id in controller: " + room_id);
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

    @GetMapping("/rooms/edit/{roomId}")
    public String updateRoom(@PathVariable("roomId") int roomId, Model model){
        RoomsDto room = roomsService.selectRoomById(roomId);
        model.addAttribute("room", room);
        return "board/rooms/rooms_edit";
    }

    @PostMapping("/rooms/update")
    public String updateRoom(RoomsDto room){
        roomsService.updateRoom(room);
        return "redirect:/board/rooms/rooms_detail";
    }

}
