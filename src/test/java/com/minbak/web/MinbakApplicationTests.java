package com.minbak.web;

import com.minbak.web.board.categories.BoardCategoriesMapper;
import com.minbak.web.board.posts.BoardPostDto;
import com.minbak.web.board.posts.BoardPostsMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MinbakApplicationTests {

	@Test
	void contextLoads() {
	}

	@Autowired
	BoardPostsMapper boardPostsMapper;

	@Autowired
	BoardCategoriesMapper boardCategoriesMapper;

	@Test
	void inputBoardData(){

		//카테고리 추가
//		for(int i = 1; i <= 10;i++){
//			String CategoryName = i+"번 카테고리";
//			boardCategoriesMapper.createCategory(CategoryName);
//		}

		//게시글 추가
		for(int i = 1; i <= 10;i++){
			BoardPostDto boardPostDto = new BoardPostDto();
			boardPostDto.setTitle(i+"번 게시글");
			boardPostDto.setAuthor("류용환");
			boardPostDto.setContent("안녕하세요," + i +"번 게시글 내용입니다.");
			boardPostDto.setCategoryId(i);
			boardPostDto.setSubject("잡담");
			boardPostsMapper.createPost(boardPostDto);
		}

	}
}
