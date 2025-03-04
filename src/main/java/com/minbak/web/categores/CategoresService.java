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
    public void createCategory(CategoresDto categoresDto) {
        categoresMapper.createCategory(categoresDto);
    }

    // 카테고리 수정
    public int updateCategory(CategoresDto categoresDto) {
        return categoresMapper.updateCategory(categoresDto);
    }

    // 카테고리 삭제
    public int deleteCategory(int id) {
        return categoresMapper.deleteCategory(id);
    }


    //카테고리 순서 업데이트
    public void updateCategoryOrder(List<Integer> newOrder){

        //카테고리 수만큼 돌면서 해당 id카테고리의 order값을 i+1로 수정
        for (int i = 0; i < newOrder.size(); i++) {
            Integer categoryId = newOrder.get(i);

            categoresMapper.updateCategoryOrder(categoryId, i+1);
        }
    }
}