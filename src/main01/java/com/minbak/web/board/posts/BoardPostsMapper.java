package com.minbak.web.board.posts;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardPostsMapper {

    // 게시글 목록 조회 사용X
    List<BoardPostDto> findAllPosts();

    // 게시글 추가
    void createPost(BoardPostDto post);

    // 게시글 수정 사용X
    void updatePost(BoardPostDto post);

    // 게시글 삭제
    void deletePost(int id);

    // 특정 게시글 조회
    BoardPostDto findPostById(int id);

    // 모든 게시글 수 조회
    int countAllPosts();

    // 페이지에 보여줄 게시글 조회
    List<BoardPostDto> findPostsByLimitAndOffset(int limit, int offset);

    // 카테고리에 해당하는 모든 게시글 조회
    int countAllPostsByCategoryId(int categoryId);

    // 페이지에 보여줄 해당 카테고리를 갖고있는 게시글 조회
    List<BoardPostDto> findPostsByLimitAndOffsetByCategoryId(int limit, int offset, int categoryId);

}
