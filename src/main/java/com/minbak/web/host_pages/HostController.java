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
    public String location(){
        return "host-pages/location";
    }
}
