package com.minbak.web.board.posts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.plaf.PanelUI;
import java.util.List;

@Service
public class BoardPostsService {

    @Autowired
    BoardPostsMapper boardPostsMapper;

    // 모든 게시글 수 조회
    public int countAllPosts(){
        return boardPostsMapper.countAllPosts();
    }

    //페이지와 사이즈 받아서 해당 게시글 조회
    public List<BoardPostDto> findPostsByLimitAndOffset(int page, int size){

        int offset = (page-1)*size;
        return boardPostsMapper.findPostsByLimitAndOffset(size, offset);
    }

    //카테고리에 해당하는 모든 게시글 조회
    public int countAllPostsByCategoryId(int categoryId){
        return boardPostsMapper.countAllPostsByCategoryId(categoryId);
    }

    //페이지와 사이즈 카테고리를 받아서 해당하는 게시글 조회
    public List<BoardPostDto> findPostsByLimitAndOffsetByCategoryId(int page, int size, int categoryId){

        int offset = (page-1)*size;
        return boardPostsMapper.findPostsByLimitAndOffsetByCategoryId(size, offset, categoryId);
    }

    //id로 Post찾기
    public BoardPostDto findPostById(int id){
        return boardPostsMapper.findPostById(id);
    }

    //id로 Post지우기
    public void deletePost(int id){
        boardPostsMapper.deletePost(id);
    }

    //게시글 생성
    public void createPost(BoardPostDto boardPostDto){
        boardPostsMapper.createPost(boardPostDto);
    }
}

