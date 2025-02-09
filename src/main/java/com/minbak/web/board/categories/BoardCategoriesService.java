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

    //카테고리 순서 업데이트
    public void updateCategoryOrder(List<Integer> newOrder){

        //카테고리 수만큼 돌면서 해당 id카테고리의 order값을 i+1로 수정
        for (int i = 0; i < newOrder.size(); i++) {
            Integer categoryId = newOrder.get(i);

            boardCategoriesMapper.updateCategoryOrder(categoryId, i+1);
        }
    }

    //수정할 카테고리의 id와 문자열을 받아서 카테고리Dto객체 생성 후 업데이트
    public void updateCategory(int id, String categoryName){
        BoardCategoryDto boardCategoryDto = new BoardCategoryDto();
        boardCategoryDto.setId(id);
        boardCategoryDto.setName(categoryName);

        boardCategoriesMapper.updateCategory(boardCategoryDto);
    }

    //카테고리 삭제
    public void deleteCategory(int id){
        boardCategoriesMapper.deleteCategory(id);
    }

    //카테고리 추가
    public void createCategory(BoardCategoryDto boardCategoryDto){
        boardCategoriesMapper.createCategory(boardCategoryDto.getName());
    }
}
