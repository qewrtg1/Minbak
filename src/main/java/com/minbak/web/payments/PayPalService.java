package com.minbak.web.payments;

import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class PayPalService {

    @Autowired
    private APIContext apiContext;

//    total: 총 결제 금액.
//    currency: 결제 통화 (예: "USD").
//    method: 결제 방식 ("paypal" 고정).
//    intent: "sale"(즉시 결제) 또는 "authorize"(예약 결제).
//    description: 결제 설명.
//    cancelUrl: 사용자가 결제를 취소할 경우 리디렉션될 URL.
//    successUrl: 사용자가 결제를 완료할 경우 리디렉션될 URL.

    public String createPayment(BigDecimal usdAmount, String currency, String method, String intent, String description, String cancelUrl, String successUrl) throws PayPalRESTException {

        //결제 금액 설정
        Amount amount = new Amount();
        amount.setCurrency(currency);
        amount.setTotal(String.format("%.2f", usdAmount));

        //거래 정보 설정
        Transaction transaction = new Transaction();
        transaction.setDescription(description);
        transaction.setAmount(amount);

        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction);

        //결제 수단 설정
        Payer payer = new Payer();
        payer.setPaymentMethod(method);

        //최종 결제 객체 생성
        Payment payment = new Payment();
        payment.setIntent(intent);
        payment.setPayer(payer);
        payment.setTransactions(transactions);

        //결제 성공 및 취소 url설정
        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl(cancelUrl);
        redirectUrls.setReturnUrl(successUrl);
        payment.setRedirectUrls(redirectUrls);

        System.out.println(payment);
        System.out.println(apiContext);
        //paypal에 결제 요청을 전송
        // APIContext를 사용하여 PayPal API에 요청
        //createdPayment는 승인 url를 반환받음
        Payment createdPayment = payment.create(apiContext);

        //paypal에서 승인 url을 찾아서 반환
        for (Links link : createdPayment.getLinks()) {
            if (link.getRel().equals("approval_url")) {
                return link.getHref();
            }
        }
        return null;
    }

    public Payment executePayment(String paymentId, String payerId) throws PayPalRESTException {
        Payment payment = new Payment();
        payment.setId(paymentId);
        PaymentExecution paymentExecution = new PaymentExecution();
        paymentExecution.setPayerId(payerId);
        return payment.execute(apiContext, paymentExecution);
    }

}
