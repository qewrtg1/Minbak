package com.minbak.web.review;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class ReviewService {

    private final ReviewMapper reviewMapper;
    private final BookingMapper bookingMapper; // 예약 정보를 가져오기 위한 Mapper

    @Autowired
    public ReviewService(ReviewMapper reviewMapper, BookingMapper bookingMapper) {
        this.reviewMapper = reviewMapper;
        this.bookingMapper = bookingMapper;
    }

    //  모든 리뷰 목록 가져오기
    public List<ReviewDto> findAllReview() {
        return reviewMapper.findAllReview();
    }

    //  최신순으로 리뷰 가져오기
    public List<ReviewDto> findOrderedReview() {
        return reviewMapper.findOrderedReview();
    }

    //  특정 리뷰 상세보기
    public ReviewDto findReviewById(int reviewId) {
        return reviewMapper.findReviewById(reviewId);
    }

    // 리뷰 수정
    public void editReview(ReviewDto review) {
        ReviewDto existingReview = reviewMapper.findReviewById(review.getReviewId());
        if (existingReview == null) {
            throw new IllegalArgumentException("해당 리뷰가 존재하지 않습니다: " + review.getReviewId());
        }
        reviewMapper.editReview(review);
    }

    //  리뷰 삭제
    public void deleteReview(int id) {
        reviewMapper.deleteReview(id);
    }

    // 리뷰 추가
    public void createReview(ReviewDto reviewDto) {
        reviewMapper.createReview(reviewDto);
    }




    //  특정 호스트가 답변해야 할 리뷰 목록 가져오기
    public List<ReviewDto> getUnansweredReviews(int hostId) {
        return reviewMapper.findUnansweredReviewsByHost(hostId);
    }

    // 특정 리뷰에 대한 호스트 답변 저장
    public void addHostReply(int reviewId, String hostReply) {
        reviewMapper.updateHostReply(reviewId, hostReply);
    }





    //  User가 체크아웃 후 2주 이내에만 리뷰 작성 가능
    public boolean canUserWriteReview(int userId, int bookId) {
        BookingDto booking = bookingMapper.getBookingById(bookId);

        if (booking == null || !"완료".equals(booking.getStatus())) {
            return false; // 예약이 존재하지 않거나 완료되지 않으면 리뷰 작성 불가
        }

        LocalDate checkoutDate = booking.getEndDate(); // 체크아웃 날짜
        LocalDate today = LocalDate.now();

        return ChronoUnit.DAYS.between(checkoutDate, today) <= 14; // 14일 이내 확인
    }




    // Host는 User가 리뷰 작성 후 2주 이내에만 답변 가능
    public boolean canHostReply(int reviewId) {
        ReviewDto review = reviewMapper.findReviewById(reviewId);

        if (review == null) {
            return false;
        }

        LocalDate reviewDate = review.getCreatedAt().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate today = LocalDate.now();

        return ChronoUnit.DAYS.between(reviewDate, today) <= 14; // 14일 이내 확인
    }

    // 리뷰 공개 여부 확인 (User와 Host가 모두 작성해야 공개됨)
    public boolean isReviewPublic(int reviewId) {
        return reviewMapper.isReviewPublic(reviewId);
    }
}
