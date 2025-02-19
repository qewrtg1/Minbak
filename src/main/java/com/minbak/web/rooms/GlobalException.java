package com.minbak.web.rooms;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalException {
    @ExceptionHandler(RoomException.class)
    public String RoomError(RoomException ex, Model model){
        model.addAttribute("errorMessage", ex.getMessage());
        return "rooms/rooms_error";
    }
}
