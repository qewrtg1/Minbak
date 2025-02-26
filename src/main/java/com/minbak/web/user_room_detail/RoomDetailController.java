package com.minbak.web.user_room_detail;


import com.minbak.web.user_room_detail.dto.RoomDetailDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class RoomDetailController {

    private final RoomDetailService roomDetailService;

    @GetMapping("/room/{roomId}")
    public String getRoomDetail(@PathVariable int roomId, Model model) {
        RoomDetailDto roomDetail = roomDetailService.getRoomDetail(roomId);
        model.addAttribute("room", roomDetail.getRoom());
        model.addAttribute("host", roomDetail.getHost());
        model.addAttribute("reviews", roomDetail.getReviews());

        System.out.println(roomDetail.getHost());
        return "/user-pages/room-detail";
    }
}
