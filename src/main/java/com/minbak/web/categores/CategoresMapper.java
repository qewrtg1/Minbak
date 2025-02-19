package com.minbak.web.categores;

import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface CategoresMapper {

        // 전체 카테고리 조회
        List<CategoresDto> findAllCategories();

        // 특정 카테고리 조회 (ID 기준)
        CategoresDto findCategoryById(int id);

        // 새 카테고리 추가
        int createCategory(CategoresDto categoresDto);

        // 카테고리 수정
        int updateCategory(CategoresDto categoresDto);

        // 카테고리 삭제
        int deleteCategory(int id);

}
