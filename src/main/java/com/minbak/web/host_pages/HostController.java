package com.minbak.web.host_pages;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/host")
public class HostController {

    @GetMapping("/create-rooms")
    public String createRooms(){
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

    @GetMapping("/title")
    public String roomsTitle(){
        return "host-pages/title";
    }

    @GetMapping("/description")
    public String description(){
        return "host-pages/description";
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
}
