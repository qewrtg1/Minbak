package com.minbak.web.review;

import com.minbak.web.books.BooksDto;
import com.minbak.web.email.EmailDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

@Service
public class ReviewService {

    @Autowired
    ReviewMapper reviewMapper;
    @Autowired
    ReviewBooksMapper reviewBooksMapper;

    @Autowired  // Spring에서 자동으로 의존성 주입
    public ReviewService(ReviewMapper reviewMapper, ReviewBooksMapper reviewBooksMapper) {
        this.reviewMapper = reviewMapper;
        this.reviewBooksMapper = reviewBooksMapper;
    }

    // 검색어에 맞는 리뷰 리스트를 반환하는 메서드
    public List<ReviewDto> getReviews(int offset, int size) {  // 리턴 타입과 메서드 이름을 명확히 작성
        List<ReviewDto> reviews = reviewMapper.getReviews(offset, size);
        return reviewMapper.getReviews(offset, size);  // Mapper에서 검색된 리뷰 리스트를 반환
    }

    // 검색어에 맞는 총 후기 수를 반환하는 메서드
    public Integer getTotalReviewCount(String search) {  // 리턴 타입을 Integer로 명시
        return reviewMapper.getTotalReviewCount(search);  // Mapper에서 총 후기 수를 반환
    }


    // 검색어에 맞는 리뷰 리스트를 반환하는 메서드
    public List<ReviewDto> searchReview(int offset, int size, String search) {
        return reviewMapper.searchReview(offset, size, search) ;  // Mapper에서 검색된 리뷰 리스트를 반환
    }

    // 모든 리뷰 목록 가져오기
    public List<ReviewDto> findAllReview() {
        return reviewMapper.findAllReview();
    }

    //모든 리뷰 순서대로 가져오기
    public List<ReviewDto> findOrderedReview() {
        return reviewMapper.findOrderedReview();
    }

    // 리뷰 상세보기
    public ReviewDto findReviewById(int reviewId) {
        return reviewMapper.findReviewById(reviewId);
    }



    // 리뷰 수정 ()
    public void editReview(ReviewDto review) {
        ReviewDto existingReview = reviewMapper.findReviewById(review.getReviewId());
        if (existingReview == null) {
            throw new IllegalArgumentException("해당 리뷰가 존재하지 않습니다: " + review.getReviewId());
        }
        reviewMapper.editReview(review);
    }


    // 블라인드 처리 기능 (새로운 기능 추가)
    public void blindReview(int reviewId, int isBlind) {
        if(isBlind == 0) reviewMapper.blindReview(reviewId);
        else reviewMapper.unblindReview(reviewId);
    }

    // 부적절한 리뷰 표시 기능 (새로운 기능 추가)
    public void markAsInappropriate(int reviewId) {
        reviewMapper.markAsInappropriate(reviewId);
    }



    ////////////////////// user ///////////////////////////////
    public BooksDto getBookData(int bookId){
        System.out.println(reviewBooksMapper.selectBookById(bookId));
        return reviewBooksMapper.selectBookById(bookId);
    }

    public int createReview(ReviewDto reviewDto){
        if (reviewMapper.createReview(reviewDto) >= 1){
            return 1;
        }else{
            return 0;
        }
    }
    //////////////////// Email ////////////////////

    public List<EmailDto> setEmail(){
        List<Map<String, Object>> booksDtoList = reviewBooksMapper.getWaitingReview();
        List<EmailDto> emailDtoList = null;
        for(Map<String, Object> booksDto : booksDtoList){
            EmailDto emailDto =  EmailDto.builder().to((String)booksDto.get("email")).title("리뷰 작성 요청").message("지난 여행은 즐거우셨나요? 리뷰를 작성해 주세요!").build();
            emailDtoList.add(emailDto);
        }
        return emailDtoList;
    }


    ////////////////////// host ///////////////////////////////
    public List<ReviewDto> getUnrepliedReview(int hostId){
        return reviewBooksMapper.getUnrepliedReview(hostId);
    }

    public Integer replyReview(int reviewId){
        ReviewDto reviewDto = new ReviewDto();
        reviewDto.setReviewId(reviewId);
        if(reviewBooksMapper.replyReview(reviewDto) >= 1){
            return 1;
        }else{
            return 0;
        }
    }

    public Integer deleteReview(int reviewId){
        if(reviewBooksMapper.deleteReview(reviewId) >= 1){
            return 1;
        }else{
            return 0;
        }
    }

    ////////////////////// room ///////////////////////////////
    public List<ReviewDto> getRoomReviews(int hostId){
        return reviewBooksMapper.getRoomReviews(hostId);
    }



}



