package com.minbak.web.categores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin/categories")
public class CategoresController {

    private final CategoresService categoresService;

    @Autowired
    public CategoresController(CategoresService categoresService) {
        this.categoresService = categoresService;
    }

    // [R] 전체 카테고리 목록 조회
    @GetMapping
    public String showCategoriesList(Model model) {
        model.addAttribute("categories", categoresService.getAllCategories());
        return "categores/categores-List";  // templates/categores/categores-List.html
    }

    // [C] 신규 카테고리 등록 폼 표시
    @GetMapping("/create")
    public String showCreateCategoryForm(Model model) {
        model.addAttribute("category", new CategoresDto());
        return "categores/category-Create";  // 템플릿 파일 이름에 맞게 변경
    }

    // [C] 신규 카테고리 등록 처리
    @PostMapping
    public String createCategory(@ModelAttribute("category") CategoresDto categoresDto) {
        categoresService.createCategory(categoresDto);
        return "redirect:/admin/categories"; // 리다이렉트 URL 수정
    }

    // [U] 기존 카테고리 수정 폼 표시
    @GetMapping("/edit/{id}")
    public String showEditCategoryForm(@PathVariable int id, Model model) {
        CategoresDto category = categoresService.getCategoryById(id);
        model.addAttribute("category", category);
        return "categores/category-Update";  // 수정 폼 템플릿
    }

    // [U] 카테고리 수정 처리
    @PostMapping("/edit/{id}")
    public String updateCategory(@PathVariable int id, @ModelAttribute("category") CategoresDto categoresDto) {
        categoresDto.setCategoryId(id);
        categoresService.updateCategory(categoresDto);
        return "redirect:/admin/categories";
    }

    // [D] 카테고리 삭제 처리
    @PostMapping("/delete/{id}")
    public String deleteCategory(@PathVariable int id) {
        categoresService.deleteCategory(id);
        return "redirect:/admin/categories";
    }



}
