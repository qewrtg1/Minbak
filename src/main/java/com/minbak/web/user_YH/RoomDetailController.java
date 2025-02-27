package com.minbak.web.user_YH;


import com.minbak.web.spring_security.CustomUserDetails;
import com.minbak.web.user_YH.dto.RoomDetailDto;
import com.minbak.web.users.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class RoomDetailController {

    private final RoomDetailService roomDetailService;

    private final UsersService usersService;

    @GetMapping("/room/{roomId}")
    public String getRoomDetail(@PathVariable int roomId, Model model,
                                @AuthenticationPrincipal CustomUserDetails userDetails) {

        if(userDetails !=null){
            model.addAttribute("userUrl",roomDetailService.findImageUrlsByUserId(userDetails.getUserId()));
            model.addAttribute("user", usersService.findUserByUserId(userDetails.getUserId()));
        }

        RoomDetailDto roomDetail = roomDetailService.getRoomDetail(roomId);
        roomDetail.getRoom().setOptions(roomDetailService.getRoomOptions(roomId));

        model.addAttribute("maxGuests", roomDetail.getMaxGuests());
        model.addAttribute("room", roomDetail.getRoom());
        model.addAttribute("host", roomDetail.getHost());
        model.addAttribute("reviews", roomDetail.getReviews());

        List<Map<String, LocalDate>> reservedDates = roomDetailService.getReservedDates(roomId);
        model.addAttribute("reservedDates", reservedDates);

        System.out.println(roomDetail.getHost());
        return "/user-pages/room-detail";
    }
}
