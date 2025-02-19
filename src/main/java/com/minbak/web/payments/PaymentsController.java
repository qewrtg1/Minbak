package com.minbak.web.payments;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/payments")
public class PaymentsController {

    @Autowired
    private PaymentsService paymentsService;

    // 결제 정보 목록 조회
    @GetMapping
    public String getAllPayments(Model model) {
        model.addAttribute("payments", paymentsService.getAllPayments());
        return "payments/payment-list";  // payments/paymentList.html로 이동
    }

    // 결제 정보 조회
    @GetMapping("/{paymentId}")
    public String getPaymentById(@PathVariable int paymentId, Model model) {
        model.addAttribute("payment", paymentsService.getPaymentById(paymentId));
        return "payments/payment-detail";  // payments/paymentDetail.html로 이동
    }

    // 결제 정보 추가 폼
    @GetMapping("/new")
    public String createPaymentForm(Model model) {
        model.addAttribute("paymentDto", new PaymentDto()); // 새 객체 전달
        return "payments/payment-form";  // payments/paymentForm.html로 이동
    }

    // 결제 정보 추가
    @PostMapping("/create")
    public String createPayment(@ModelAttribute PaymentDto paymentDto) {
        paymentsService.createPayment(paymentDto);
        return "redirect:/payments";  // 결제 정보 목록으로 리다이렉트
    }

    // 결제 정보 수정 폼
    @GetMapping("/edit/{paymentId}")
    public String editPaymentForm(@PathVariable int paymentId, Model model) {
        model.addAttribute("paymentDto", paymentsService.getPaymentById(paymentId));
        return "payments/payment-form";  // payments/paymentForm.html로 이동
    }

    // 결제 정보 수정
    @PostMapping("/edit")
    public String updatePayment(@ModelAttribute PaymentDto paymentDto) {
        paymentsService.updatePayment(paymentDto);
        return "redirect:/payments";  // 결제 정보 목록으로 리다이렉트
    }

    // 결제 정보 삭제
    @GetMapping("/delete/{paymentId}")
    public String deletePayment(@PathVariable int paymentId) {
        paymentsService.deletePayment(paymentId);
        return "redirect:/payments";  // 결제 정보 목록으로 리다이렉트
    }
}
