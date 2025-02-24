package com.minbak.web;

import com.minbak.web.board.categories.BoardCategoriesMapper;
import com.minbak.web.board.comments.BoardCommentDto;
import com.minbak.web.board.comments.BoardCommentsMapper;
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
	BoardCommentsMapper boardCommentsMapper;

	@Autowired
	BoardPostsMapper boardPostsMapper;

	@Autowired
	BoardCategoriesMapper boardCategoriesMapper;

	@Test
	void inputBoardData() throws InterruptedException {

		//카테고리 추가
//		for(int i = 1; i <= 10;i++){
//			String CategoryName = i+"번 카테고리";
//			boardCategoriesMapper.createCategory(CategoryName);
//			Thread.sleep(100);
//		}

		//게시글 추가
//		for(int i = 1; i <= 10;i++){
//			BoardPostDto boardPostDto = new BoardPostDto();
//			boardPostDto.setTitle(i+"번 게시글");
//			boardPostDto.setAuthor("류용환");
//			boardPostDto.setContent("안녕하세요," + i +"번 게시글 내용입니다.");
//			boardPostDto.setCategoryId(i);
//			boardPostDto.setSubject("잡담");
//			boardPostsMapper.createPost(boardPostDto);
//		}

		//댓글 추가
//		for(int i = 2; i <= 10;i++){
//			BoardCommentDto boardCommentDto = new BoardCommentDto();
//			boardCommentDto.setPostId(i);
//			boardCommentDto.setAuthor("작성자"+i);
//			boardCommentDto.setContent("게시글에 공감합니다"+i+"번 내용입니다.");
//			boardCommentsMapper.createComment(boardCommentDto);
//		}

	}
}
