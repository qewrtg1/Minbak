package com.minbak.web.payments;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PaymentController {

    @GetMapping("/paypal")
    //테스트 html url
    public String testPaymentPage(){
        return "payment/test-paypal";
    }


    @GetMapping("/portone")
    //테스트 html url
    public String testPaymentPage2(){
        return "payment/test-port-one";
    }

}
