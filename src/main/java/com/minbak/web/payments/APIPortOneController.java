package com.minbak.web.payments;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import java.util.Map;

@RestController
@RequestMapping("/api/portone")
public class APIPortOneController {

    private final PortOneService portOneService;

    public APIPortOneController(PortOneService portOneService) {
        this.portOneService = portOneService;
    }

    //impUid를 받아 결제정보 요청
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
