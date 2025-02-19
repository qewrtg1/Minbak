package com.minbak.web.board.categories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/board/admin/categories")
public class BoardCategoriesController {

    @GetMapping
    public String adminCategoriesPage(){
        return "/board/admin/categories";
    }

    @GetMapping("/create")
    public String adminCategoriesCreatePage(){
        return "/board/admin/create-category";
    }

}