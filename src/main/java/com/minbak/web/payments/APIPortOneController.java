package com.minbak.web.payments;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/portone")
@RequiredArgsConstructor
public class APIPortOneController {

    private final PortOneService portOneService;


    @PostMapping("/verify")
    public ResponseEntity<?> verifyPayment(@RequestBody Map<String, String> requestData) {
        try {
            String impUid = requestData.get("impUid");

            // 포트원 API에서 결제 정보 조회
            PaymentInfoDto paymentInfo = portOneService.getPaymentInfoSync(impUid);
            System.out.println("결제 정보: " + paymentInfo);

            // DB에서 `merchantUid`로 결제 요청 정보 조회
            PaymentDto storedPayment = portOneService.getPaymentByMerchantUid(paymentInfo.getMerchantUid());

            if (storedPayment == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(Map.of("error", "결제 요청 정보가 없습니다."));
            }

            // 결제 금액 검증
            if (storedPayment.getAmount().compareTo(BigDecimal.valueOf(paymentInfo.getAmount())) != 0) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(Map.of("error", "결제 금액 불일치! 예상: " + storedPayment.getAmount() + ", 실제: " + paymentInfo.getAmount()));
            }

            // 결제 상태 검증 (`paid` 상태인지 확인)
            if (!"paid".equals(paymentInfo.getStatus())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(Map.of("error", "결제 상태가 완료되지 않았습니다."));
            }

            // 결제 승인 & DB 업데이트
            portOneService.updatePaymentStatus(storedPayment.getMerchantUid(), impUid);


            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "message", "결제 검증 성공",
                    "amount", paymentInfo.getAmount()
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "결제 검증 중 오류 발생: " + e.getMessage()));
        }
    }



    @PostMapping("/order")
    public ResponseEntity<?> createOrder(@RequestBody PaymentDto paymentDto) {
        try {
            System.out.println("🔹 요청 데이터: " + paymentDto);

            int bookId = paymentDto.getBookId();
            int userId = paymentDto.getUserId();
            BigDecimal amount = new BigDecimal(paymentDto.getAmount().toString());
            String method = paymentDto.getMethod();

            // 주문번호 생성 및 저장
            String merchantUid = portOneService.createPaymentOrder(bookId, userId, amount, method);

            return ResponseEntity.ok(Map.of("orderId", merchantUid));
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "주문 생성 실패: " + e.getMessage()));
        }
    }



    //impUid를 받아 결제정보 요청 초기작업
    @PostMapping("/payment-info")
    public ResponseEntity<PaymentInfoDto> getPaymentInfo(@RequestParam String impUid) {
        PaymentInfoDto paymentInfo = portOneService.getPaymentInfoSync(impUid);
        System.out.println("결제 정보"+paymentInfo);


        // 예제: 예상 결제 금액과 비교 (데이터베이스에서 가져와 비교 가능)
//        int expectedAmount = 60000; // 예제 금액, 실제로는 DB에서 가져와야 함
//        if (paymentInfo.getAmount() != expectedAmount) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                    .body(paymentInfo);
//        }

        // 결제 상태 확인
//        if (!"paid".equals(paymentInfo.getStatus())) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                    .body(paymentInfo);
//        }


        //여기 안에, 구매자의 예약내역 조회(해당 숙소 상품이랑 날짜 등등 비교) 후 가격 비교해서 맞으면 결제 승인
        //
        // 웹훅 구현(결제 직후 연결 끊겼을 때 대처) 포트원 웹사이트에서 확인 가능
        return ResponseEntity.ok(paymentInfo);
    }
}
