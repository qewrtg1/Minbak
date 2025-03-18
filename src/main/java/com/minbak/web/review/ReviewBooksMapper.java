package com.minbak.web.review;

import com.minbak.web.books.BooksDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface ReviewBooksMapper {

    BooksDto selectBookById (Integer bookId);
    List<BooksDto> getWaitingReview();

    ////////////////////// host ///////////////////////////////
    // 호스트가 답변해야 할 리뷰 목록을 가져오는 메서드
    List<ReviewDto> getUnrepliedReview(int hostId);

    // 호스트가 특정 리뷰에 답변을 추가하는 메서드
    int replyReview(ReviewDto reviewDto);

    // 호스트가 특정 리뷰에 답변을 삭제하는 메서드
    Integer deleteReview(int reviewId);

    ////////////////////// room ///////////////////////////////
    // 방에 대한 리뷰 목록을 가져오는 메서드
    List<ReviewDto> getRoomReviews(int roomId);


}
