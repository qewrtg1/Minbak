package com.minbak.web.payments;

import com.minbak.web.books.BooksMapper;
import com.minbak.web.common.dto.PageDto;
import com.minbak.web.rooms.RoomsMapper;
import com.minbak.web.users.UsersMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class PaymentsService {

    @Autowired
    private PaymentsMapper paymentsMapper;
    @Autowired
    BooksMapper booksMapper;
    @Autowired
    UsersMapper usersMapper;

    // 결제 정보 조회
    public PaymentDto getPaymentById(int paymentId) {
        return paymentsMapper.findPaymentById(paymentId);
    }

    // 결제 정보 목록 조회
    public List<PaymentDto> getAllPayments() {
        return paymentsMapper.findAllPayments();
    }

    // 결제 정보 추가
    public void createPayment(PaymentDto paymentDto) {
        paymentsMapper.createPayment(paymentDto);
    }

    // 결제 정보 수정
    public void updatePayment(PaymentDto paymentDto) {
        paymentsMapper.updatePayment(paymentDto);
    }

    // 결제 정보 삭제
    public void deletePayment(int paymentId) {
        paymentsMapper.deletePayment(paymentId);
    }

    // 특정 사용자에 의한 결제 정보 조회
    public List<PaymentDto> getPaymentsByUserId(int userId) {
        return paymentsMapper.findPaymentsByUserId(userId);
    }

    // 특정 예약에 대한 결제 정보 조회
    public List<PaymentDto> getPaymentsByBookId(int bookId) {
        return paymentsMapper.findPaymentsByBookId(bookId);
    }

    //페이징
    public PageDto<PaymentDto> findPaymentsWithLimitAndOffset(int page, int size){
        int offset = (page-1)*size;

        List<PaymentDto> paymentDtos = paymentsMapper.findPaymentsWithLimitAndOffset(size,offset);

        int totalPage = paymentsMapper.countPayments();
        for (PaymentDto paymentDto : paymentDtos){
            paymentDto.setUserEmail(usersMapper.findUserByUserId(paymentDto.getUserId()).getEmail());
            paymentDto.setRoomName(booksMapper.selectBookById(paymentDto.getBookId()).getRoom().getName());
//            paymentDto.setStartDate(booksMapper.selectBookById(paymentDto.getBookId()).getStartDate());
//            paymentDto.setEndDate(booksMapper.selectBookById(paymentDto.getBookId()).getEndDate());
            paymentDto.setDateDifference(ChronoUnit.DAYS
                    .between(booksMapper.selectBookById(paymentDto.getBookId()).getStartDate()
                            ,booksMapper.selectBookById(paymentDto.getBookId()).getEndDate()));
        }

        return new PageDto<>(page,size,totalPage,paymentDtos);
    }

    //예약상태 업데이트
    public void updateBookStatus(String status, int bookId) {
        paymentsMapper.updateBookStatus(status,bookId);
    }

    // 예약 필터 적용하여 결제 정보 조회
    public PageDto<PaymentDto> filterPaymentsByPaymentDto(RequestPaymentDto requestPaymentDto,int page, int size){
        int offset = (page-1)*size;
        int totalPage = paymentsMapper.countFilterPayments(requestPaymentDto);
        requestPaymentDto.setLimit(size);
        requestPaymentDto.setOffset(offset);
        List<PaymentDto> paymentDtos=paymentsMapper.filterPaymentsByPaymentDto(requestPaymentDto);
        for (PaymentDto paymentDto : paymentDtos){
            paymentDto.setUserEmail(usersMapper.findUserByUserId(paymentDto.getUserId()).getEmail());
            paymentDto.setRoomName(booksMapper.selectBookById(paymentDto.getBookId()).getRoom().getName());
//            paymentDto.setStartDate(booksMapper.selectBookById(paymentDto.getBookId()).getStartDate());
//            paymentDto.setEndDate(booksMapper.selectBookById(paymentDto.getBookId()).getEndDate());
            paymentDto.setDateDifference(ChronoUnit.DAYS
                    .between(booksMapper.selectBookById(paymentDto.getBookId()).getStartDate()
                            ,booksMapper.selectBookById(paymentDto.getBookId()).getEndDate()));

        }
       PageDto<PaymentDto> pageDto=new PageDto<>(page,size,totalPage,paymentDtos);
        return  pageDto;
    }

}
