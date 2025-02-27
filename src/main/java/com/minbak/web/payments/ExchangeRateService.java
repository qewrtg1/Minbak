package com.minbak.web.payments;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.math.BigDecimal;
import java.util.Map;

@Service
public class ExchangeRateService {

    private static final String API_URL = "https://api.exchangerate-api.com/v4/latest/KRW";

    private final RestTemplate restTemplate;

    public ExchangeRateService() {
        this.restTemplate = new RestTemplate();
    }

    public BigDecimal convertKRWtoUSD(BigDecimal krwAmount) {
        Map<String, Object> response = restTemplate.getForObject(API_URL, Map.class);

        if (response == null || !response.containsKey("rates")) {
            throw new RuntimeException("환율 정보를 가져올 수 없습니다.");
        }

        Map<String, Object> rates = (Map<String, Object>) response.get("rates");
        BigDecimal usdRate = new BigDecimal(rates.get("USD").toString());  // ✅ 1 USD = X KRW (예: 1434.72)

        // 🚀 올바른 환율 변환 방식 적용 (KRW → USD)
        return krwAmount.multiply(usdRate).setScale(2, BigDecimal.ROUND_HALF_UP);

    }
}
