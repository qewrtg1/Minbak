package com.minbak.web.payments;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PaymentsMapper {

    // 결제 정보 조회
    PaymentDto findPaymentById(int paymentId);

    // 결제 정보 목록 조회
    List<PaymentDto> findAllPayments();

    // 결제 정보 추가
    void createPayment(PaymentDto paymentDto);

    // 결제 정보 수정
    void updatePayment(PaymentDto paymentDto);

    // 결제 정보 삭제
    void deletePayment(int paymentId);

    // 특정 사용자에 의한 결제 정보 조회
    List<PaymentDto> findPaymentsByUserId(int userId);

    // 특정 예약에 대한 결제 정보 조회
    List<PaymentDto> findPaymentsByBookId(int bookId);
}
