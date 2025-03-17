package com.minbak.web.categories;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/categories")
public class CategoriesController {

    private final CategoriesService categoriesService;

    @Autowired
    public CategoriesController(CategoriesService categoriesService) {
        this.categoriesService = categoriesService;
    }

    // [R] 전체 카테고리 목록 조회
    @GetMapping
    public String showCategoriesList(Model model) {
        model.addAttribute("categories", categoriesService.getAllCategories());
        return "categories/categories-List";  // templates/categores/categories-List.html
    }

    // [C] 신규 카테고리 등록 폼 표시
    @GetMapping("/create")
    public String showCreateCategoryForm(Model model) {
        model.addAttribute("category", new CategoriesDto());
        return "categories/category-Create";  // 템플릿 파일 이름에 맞게 변경
    }

    // [C] 신규 카테고리 등록 처리
    @PostMapping
    public String createCategory(@Valid @ModelAttribute("category") CategoriesDto categoriesDto, BindingResult result) {
        if (result.hasErrors()) {
            return "admin/category-Create"; // 유효성 검사 실패 시 다시 입력 폼으로 이동
        }
        categoriesService.createCategory(categoriesDto);
        return "redirect:/admin/categories"; // 성공 시 카테고리 목록 페이지로 이동
    }

    // [U] 기존 카테고리 수정 폼 표시
    @GetMapping("/edit/{id}")
    public String showEditCategoryForm(@PathVariable int id, Model model) {
        CategoriesDto category = categoriesService.getCategoryById(id);
        model.addAttribute("category", category);
        return "categories/category-Update";  // 수정 폼 템플릿
    }

    // [U] 카테고리 수정 처리
    @PostMapping("/edit/{id}")
    public String updateCategory(@PathVariable int id, @ModelAttribute("category") CategoriesDto categoriesDto) {
        categoriesDto.setCategoryId(id);
        categoriesService.updateCategory(categoriesDto);
        return "redirect:/admin/categories";
    }

    // [D] 카테고리 삭제 처리
    @PostMapping("/delete/{id}")
    public String deleteCategory(@PathVariable int id) {
        categoriesService.deleteCategory(id);
        return "redirect:/admin/categories";
    }



}
