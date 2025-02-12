package com.minbak.web.board.categories;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardCategoriesMapper {

    // 카테고리 목록 조회
    List<BoardCategoryDto> findAllCategories();

    // 카테고리 순서대로 목록 조회
    List<BoardCategoryDto> findOrderedCategories();

    // 카테고리 추가
    void createCategory(String categoryName);

    // 카테고리 수정
    void updateCategory(BoardCategoryDto category);

    // 카테고리 삭제
    void deleteCategory(int id);

    //카테고리id랑 카테고리order 받아서 카데고리 순서 업데이트
    void updateCategoryOrder(Integer id, int order);

    // 특정 카테고리 조회
    BoardCategoryDto findCategoryById(int id);

}
