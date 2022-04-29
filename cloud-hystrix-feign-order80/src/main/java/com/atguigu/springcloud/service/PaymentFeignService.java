package com.atguigu.springcloud.service;

import com.atguigu.springcloud.service.impl.FeignFallbackService;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(name = "HYSTRIX-PAYMENT-SERVER",fallback = FeignFallbackService.class)
public interface PaymentFeignService {

    @GetMapping("/payment/ok/{id}")
    public String paymentInfo_OK(@PathVariable("id") Integer id);

    @GetMapping("/payment/timeout/{id}")
    public String paymentInfo_TimeOut(@PathVariable("id") Integer id);

    @GetMapping("/payment/circuit/{id}")
    public String paymentCircuitBreaker(@PathVariable("id")Integer id);

    @GetMapping("/payment/flowlimit/{id}")
    public String paymentFlowlimit(@PathVariable("id") Integer id);
}
