package com.minbak.web.payments;

import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/paypal")
public class APIPayPalController {

    @Value("${server.url}")
    private String serverUrl;

    @Autowired
    PayPalService payPalService;

    @Autowired
    ExchangeRateService exchangeRateService;

    @Autowired
    PaymentsService paymentsService;


    @PostMapping("/pay")
    public ResponseEntity<?> payment(@RequestBody Map<String, Object> paymentData) {
        try {
            // 요청 데이터 추출
            double amount = Double.parseDouble(paymentData.get("amount").toString());
            int bookId = Integer.parseInt(paymentData.get("bookId").toString());
            int userId = Integer.parseInt(paymentData.get("userId").toString());
            String method = paymentData.get("method").toString();

            System.out.println("Processing PayPal payment for bookId: " + bookId + ", userId: " + userId);

            // KRW → USD 변환
            BigDecimal krwAmount = BigDecimal.valueOf(amount);
            BigDecimal usdAmount = exchangeRateService.convertKRWtoUSD(krwAmount);

            // PayPal 결제 URL 생성
            String redirectUrl = payPalService.createPayment(usdAmount, "USD", "paypal",
                    "sale", "숙박 결제",
                    serverUrl + "/api/paypal/cancel?bookId=" + bookId + "&userId=" + userId + "&method=" + method,
                    serverUrl + "/api/paypal/success?bookId=" + bookId);

            return ResponseEntity.ok(Map.of("redirectUrl", redirectUrl));
        } catch (PayPalRESTException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Payment creation failed."));
        }
    }


    @GetMapping("/success")
    public ResponseEntity<?> success(@RequestParam("paymentId") String paymentId,
                                     @RequestParam("PayerID") String payerId,
                                     @RequestParam("bookId") int bookId,
                                     @RequestParam("userId") int userId,
                                     @RequestParam("method") String method) {
        try {
            Payment payment = payPalService.executePayment(paymentId, payerId);

            // 결제 성공 시 DB에 저장
            PaymentDto paymentDto = new PaymentDto();
            paymentDto.setUserId(userId);
            paymentDto.setBookId(bookId);
            paymentDto.setAmount(new BigDecimal(payment.getTransactions().get(0).getAmount().getTotal()));
            paymentDto.setStatus("결제 완료");
            paymentDto.setMethod(method);
            paymentDto.setTransactionId(payment.getId());
            paymentDto.setPaidAt(LocalDateTime.now());

            paymentsService.createPayment(paymentDto);  // 결제 정보를 데이터베이스에 저장

            // 예약 상태 업데이트 (예: '결제 완료' 상태로 변경)
            paymentsService.updateBookStatus("완료",bookId);

            return ResponseEntity.ok(Map.of(
                    "status", "success",
                    "paymentId", payment.getId(),
                    "redirectUrl", "/payment/result"
            ));
        } catch (PayPalRESTException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Payment execution failed."));
        }
    }

    @GetMapping("/cancel")
    public ResponseEntity<?> cancel(@RequestParam(value = "bookId", required = false) Integer bookId) {
        try {
            // 예약 상태를 '결제 취소'로 변경
            paymentsService.updateBookStatus("취소", bookId);

            // 프론트엔드에서 취소 안내 페이지로 리디렉트할 URL 반환
            return ResponseEntity.ok(Map.of(
                    "status", "cancel",
                    "message", "Payment was cancelled.",
                    "redirectUrl", "/payment/cancel"
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Payment cancellation failed."));
        }
    }

}