package com.minbak.web.payments;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentsService {

    @Autowired
    private PaymentsMapper paymentsMapper;

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
}
