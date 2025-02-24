package com.minbak.web.categores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoresService {

    private final CategoresMapper categoresMapper;

    @Autowired
    public CategoresService(CategoresMapper categoresMapper) {
        this.categoresMapper = categoresMapper;
    }

    // 전체 카테고리 조회
    public List<CategoresDto> getAllCategories() {
        return categoresMapper.findAllCategories();
    }

    // 특정 카테고리 조회 (ID 기준)
    public CategoresDto getCategoryById(int id) {
        return categoresMapper.findCategoryById(id);
    }

    // 새 카테고리 생성
    public int createCategory(CategoresDto categoresDto) {
        return categoresMapper.createCategory(categoresDto);
    }

    // 카테고리 수정
    public int updateCategory(CategoresDto categoresDto) {
        return categoresMapper.updateCategory(categoresDto);
    }

    // 카테고리 삭제
    public int deleteCategory(int id) {
        return categoresMapper.deleteCategory(id);
    }
}