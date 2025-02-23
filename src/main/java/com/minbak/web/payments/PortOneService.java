package com.minbak.web.payments;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PortOneService {

    //다른 api 서버에 http url 요청할 수 있게 해주는 의존성객체
    private final WebClient webClient;

    @Value("${portone.imp.key}")  // REST API Key
    private String impKey;

    @Value("${portone.api.secret}")  // REST API Secret
    private String apiSecret;

    //엑세스 토큰 발급, 동기와 비동기 공부하기
    public String getAccessToken() {
        return webClient.post()
                .uri("https://api.iamport.kr/users/getToken")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(Map.of("imp_key", impKey, "imp_secret", apiSecret))
                .retrieve()
                .bodyToMono(Map.class)
                .flatMap(response -> {
                    if ((int) response.get("code") != 0) {
                        return Mono.error(new RuntimeException("토큰 발급 실패"));
                    }
                    return Mono.just((String) ((Map<String, Object>) response.get("response")).get("access_token"));
                })
                .block();  // ✅ 동기 방식으로 실행
    }

    // `imp_uid`와 `accessToken`을 이용한 결제 정보 조회
    public PaymentInfoDto getPaymentInfoSync(String impUid) {
        String accessToken = getAccessToken();  // ✅ 먼저 access_token 발급받기

        Map<String, Object> response = webClient.get()
                .uri("https://api.iamport.kr/payments/" + impUid)
                .header("Authorization", "Bearer " + accessToken)
                .retrieve()
                .bodyToMono(Map.class)
                .block();  // 동기 방식 실행

        if (response == null || (int) response.get("code") != 0) {
            throw new RuntimeException("결제 정보 조회 실패");
        }

        // ✅ 응답을 `PaymentInfoDto`로 변환
        return PaymentInfoDto.fromMap((Map<String, Object>) response.get("response"));
    }

}
