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

    //모든 카테고리 순서대로 가져오기
    public List<BoardCategoryDto> findOrderedCategories(){
        return boardCategoriesMapper.findOrderedCategories();
    }

    public BoardCategoryDto findCategoryById(int categoryId){
        return boardCategoriesMapper.findCategoryById(categoryId);
    }



    public void updateCategoryOrder(List<Integer> newOrder){

        for (int i = 0; i < newOrder.size(); i++) {
            Integer categoryId = newOrder.get(i);

            boardCategoriesMapper.updateCategoryOrder(categoryId, i+1);
        }
    }

    public void updateCategory(int id, String categoryName){
        BoardCategoryDto boardCategoryDto = new BoardCategoryDto();
        boardCategoryDto.setId(id);
        boardCategoryDto.setName(categoryName);

        boardCategoriesMapper.updateCategory(boardCategoryDto);
    }

    public void deleteCategory(int id){
        boardCategoriesMapper.deleteCategory(id);
    }

    public void createCategory(BoardCategoryDto boardCategoryDto){
        boardCategoriesMapper.createCategory(boardCategoryDto.getName());
    }
}
