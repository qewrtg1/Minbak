package com.minbak.web.categories;

import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface CategoriesMapper {

        // 전체 카테고리 조회
        List<CategoriesDto> findAllCategories();

        // 특정 카테고리 조회 (ID 기준)
        CategoriesDto findCategoryById(int id);

        // 새 카테고리 추가
        int createCategory(CategoriesDto categoriesDto);

        // 카테고리 수정
        int updateCategory(CategoriesDto categoriesDto);

        // 카테고리 삭제
        int deleteCategory(int id);

        //카테로기 순서 업데이트
        void updateCategoryOrder(Integer categoryId, int categoryOrder);
}
