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


    @GetMapping("/pay")
    public ResponseEntity<?> payment(@RequestParam("amount") double amount) {
        try {

            //결제 정보도 파람으로 같이 받아서 로직 실행
            //book_id, user_id, status(결제상태), method(에 따라 결제방식 if),transaction_id(=paymentId), paid_at(입력해줘야함), created_at(결제 요청 시간)

            System.out.println("Processing payment request...");

            //BigDecimal로 변경
            BigDecimal krwAmount = BigDecimal.valueOf(amount);

            // KRW → USD 변환
            BigDecimal usdAmount = exchangeRateService.convertKRWtoUSD(krwAmount);

            String redirectUrl = payPalService.createPayment(usdAmount, "USD", "paypal",
                    "sale", "Payment Description",
                    serverUrl+"/api/paypal/cancel",
                    serverUrl+"/api/paypal/success");
            // 성공 시 redirect, 실패 시 JSON 응답을 반환

            // HttpHeaders를 사용하여 리다이렉트 URL을 설정
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(URI.create(redirectUrl));

            System.out.println("Generated PayPal Redirect URL: " + redirectUrl);

            return ResponseEntity.status(HttpStatus.FOUND)  // 302 Redirect
                    .headers(headers)
                    .build();

        } catch (PayPalRESTException e) {
            // TODO 로그 남기기
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Payment creation failed."));
        }
    }

    @GetMapping("/success")
    public ResponseEntity<?> success(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId) {
        try {
            Payment payment = payPalService.executePayment(paymentId, payerId);

            // {"paymentId":"PAYID-M636YWQ9LK93425FJ417642Y","payerId":"GNAJCMU8Q7YWY","state":"approved","status":"success"}
            Map<String, Object> response = new HashMap<>();
            response.put("status", "success");
            response.put("paymentId", payment.getId());
            response.put("payerId", payment.getPayer().getPayerInfo().getPayerId());
            response.put("state", payment.getState());

            //프론트에서 어디 페이지로 리다이렉트 시킬지 알려주는 코드
            response.put("redirectUrl", "/payment/result");


            return ResponseEntity.ok().body(response);
        } catch (PayPalRESTException e) {
            // TODO 로그 남기기
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Payment execution failed."));
        }
    }

    @GetMapping("/cancel")
    public ResponseEntity<?> cancel() {
        return ResponseEntity.ok().body(Map.of("status", "cancel", "message", "Payment was cancelled."));
    }
}