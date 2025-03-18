package com.minbak.web.review;


import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ReviewGlobalException {
    @ExceptionHandler(ReviewException.class)
    public String ReviewError(ReviewException ex, Model model){
        model.addAttribute("errorMessage", ex.getMessage());
        return "review/error";
    }
}
