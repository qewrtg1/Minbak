package com.minbak.web.roomoptions;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/roomoptions")  // 모든 요청의 기본 경로 설정
public class RoomOptionsController {

    private final RoomOptionsService roomOptionsService;

    @Autowired
    public RoomOptionsController(RoomOptionsService roomOptionsService) {
        this.roomOptionsService = roomOptionsService;
    }

    // 1. 편의시설 목록 조회
    @GetMapping
    public String listRoomOptions(Model model) {
        List<RoomOptionsDto> options = roomOptionsService.getAllRoomOptions();
        model.addAttribute("options", options);
        return "roomOptions/roomOption-list";  // templates/roomOptions/roomOption-list.html 렌더링
    }

    // 2. 새 편의시설 추가 폼 표시
    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("roomOption", new RoomOptionsDto());
        return "roomOptions/roomOption-create";  // templates/roomOptions/roomOption-create.html 렌더링
    }

    // 3. 새 편의시설 추가 (폼 제출)
    @PostMapping("/create")
    public String createRoomOption(@Valid @ModelAttribute("roomOption") RoomOptionsDto roomOptionsDto,
                                   BindingResult result) {
        if (result.hasErrors()) {
            return "roomOptions/roomOption-create";
        }
        roomOptionsService.addRoomOption(roomOptionsDto);
        return "redirect:/admin/roomoptions";  // 목록 페이지로 리다이렉트
    }

    // 4. 편의시설 수정 폼 표시
    @GetMapping("/update/{optionId}")
    public String showUpdateForm(@PathVariable int optionId, Model model) {
        RoomOptionsDto roomOption = roomOptionsService.getRoomOptionById(optionId);
        if (roomOption == null) {
            return "redirect:/admin/roomoptions";
        }
        model.addAttribute("roomOption", roomOption);
        return "roomOptions/roomOption-update";  // templates/roomOptions/roomOption-update.html 렌더링
    }

    // 5. 편의시설 수정 (폼 제출)
    @PostMapping("/update/{optionId}")
    public String updateRoomOption(@PathVariable int optionId,
                                   @Valid @ModelAttribute("roomOption") RoomOptionsDto roomOptionsDto,
                                   BindingResult result) {
        if (result.hasErrors()) {
            return "roomOptions/roomOption-update";
        }
        roomOptionsDto.setOptionId(optionId);
        roomOptionsService.updateRoomOption(roomOptionsDto);
        return "redirect:/admin/roomoptions";
    }

    // 6. 편의시설 삭제
    @PostMapping("/delete/{optionId}")
    public String deleteRoomOption(@PathVariable int optionId) {
        roomOptionsService.deleteRoomOption(optionId);
        return "redirect:/admin/roomoptions";
    }


    // 편의시설 필터 적용된 숙소 리스트를 Thymeleaf에서 렌더링
    // 사용자가 선택한 편의시설을 포함하는 숙소 리스트 조회
    @GetMapping("/test")
    public String getRoomsByAmenities(@RequestParam(required = false) List<String> amenities, Model model) {
        List<RoomOptionsDto> rooms = roomOptionsService.getRoomsByAmenities(amenities != null ? amenities : List.of());
        model.addAttribute("rooms", rooms); // 모델에 숙소 리스트 추가
        return "roomOptions/roomOption-AllList"; // Thymeleaf에서 사용할 HTML 파일명 (roomOption-AllList.html)
    }
}









