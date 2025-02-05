package com.minbak.web.board.categories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardCategoriesService {

    @Autowired
    BoardCategoriesMapper boardCategoriesMapper;

    //모든 카테고리 가져오기
    public List<BoardCategoryDto> findAllCategories(){
        return boardCategoriesMapper.findAllCategories();
    }
}
