package com.minbak.web.host_pages;

import com.minbak.web.spring_security.CustomUserDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import com.minbak.web.host_pages.dto.HostDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/host")
@SessionAttributes("hostDto")
public class HostController {
    @Autowired
    private HostService hostService;

    @ModelAttribute("hostDto")
    public HostDto hostDto() {
        return new HostDto(); // 세션에 없으면 새로운 객체 반환
    }

    @GetMapping("/create-rooms")
    public String createRooms(@AuthenticationPrincipal CustomUserDetails userDetails,
                              @ModelAttribute("hostDto") HostDto hostDto,
                              Model model) {
        // userId가 설정되지 않았다면 로그인된 사용자의 ID(1번) 설정
        if(userDetails != null){
            hostDto.setUserId(userDetails.getUserId());
            System.out.println("로그인한 userId: " + userDetails.getUserId());
        }

        // userName이 아직 설정되지 않았다면 DB에서 가져오기
        if (hostDto.getUserName() == null) {
            String userName = hostService.getUserName(hostDto.getUserId());
            hostDto.setUserName(userName);
            System.out.println("DB에서 가져온 userName: " + userName); // 로그 확인
        }

        model.addAttribute("name", hostDto.getUserName());
        return "host-pages/rooms_create";
    }

    @GetMapping("/overview")
    public String overview(){
        return "host-pages/overview";
    }

    @GetMapping("/place")
    public String place(){
        return "host-pages/place";
    }

    @GetMapping("/type")
    public String roomsType(){
        return "host-pages/type";
    }

    @GetMapping("/location")
    public String roomLocation(){
        return "host-pages/location";
    }

    @GetMapping("/floor-plan")
    public String floorPlan(){
        return "host-pages/floor-plan";
    }

    @GetMapping("/charm")
    public String charm(){
        return "host-pages/accommodation-charm";
    }

    @GetMapping("/option")
    public String roomsOption(){
        return "host-pages/option";
    }

    @GetMapping("/photos")
    public String photos(){
        return "host-pages/photos";
    }

    @GetMapping("/roomsName")
    public String roomsName(){
        return "host-pages/roomsName";
    }

    @GetMapping("/title")
    public String roomsTitle(){
        return "host-pages/title";
    }

    @GetMapping("/description")
    public String description(){
        return "host-pages/description";
    }

    @GetMapping("useGuide")
    public String roomsUseGuide(){
        return "host-pages/useGuide";
    }

    @GetMapping("/finish-setup")
    public String finish(){
        return "host-pages/finish-setup";
    }

    @GetMapping("/price")
    public String roomsPrice(){
        return "host-pages/price";
    }

    @GetMapping("/receipt")
    public String receipt(){
        return "host-pages/receipt";
    }

    @GetMapping("/publish")
    public String publish(){
        return "host-pages/publish";
    }


}
