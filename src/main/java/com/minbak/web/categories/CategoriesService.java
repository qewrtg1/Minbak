package com.minbak.web.categories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriesService {

    private final CategoriesMapper categoriesMapper;

    @Autowired
    public CategoriesService(CategoriesMapper categoriesMapper) {
        this.categoriesMapper = categoriesMapper;
    }

    // 전체 카테고리 조회
    public List<CategoriesDto> getAllCategories() {
        return categoriesMapper.findAllCategories();
    }

    // 특정 카테고리 조회 (ID 기준)
    public CategoriesDto getCategoryById(int id) {
        return categoriesMapper.findCategoryById(id);
    }

    // 새 카테고리 생성
    public void createCategory(CategoriesDto categoriesDto) {
        categoriesMapper.createCategory(categoriesDto);
    }

    // 카테고리 수정
    public int updateCategory(CategoriesDto categoriesDto) {
        return categoriesMapper.updateCategory(categoriesDto);
    }

    // 카테고리 삭제
    public int deleteCategory(int id) {
        return categoriesMapper.deleteCategory(id);
    }


    //카테고리 순서 업데이트
    public void updateCategoryOrder(List<Integer> newOrder){

        //카테고리 수만큼 돌면서 해당 id카테고리의 order값을 i+1로 수정
        for (int i = 0; i < newOrder.size(); i++) {
            Integer categoryId = newOrder.get(i);

            categoriesMapper.updateCategoryOrder(categoryId, i+1);
        }
    }
}