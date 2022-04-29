package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@RestController
@Slf4j
public class OrderController {

    @Resource
    private RestTemplate restTemplate;

//    private static String PaymentSrv_URL = "http://localhost:8001";
    private static String PaymentSrv_URL = "http://CLOUD-PAYMENT-SERVICE";

    @GetMapping("/consumer/payment/port")
    public String getPort(){
        return restTemplate.getForObject(PaymentSrv_URL+"/payment/port", String.class);
    }
    @GetMapping("/consumer/payment/get/{id}")
    public CommonResult getPaymentById(@PathVariable("id") Long id){
        return restTemplate.getForObject(PaymentSrv_URL+"/payment/get/"+id, CommonResult.class,id );
    }

    @PostMapping("/consumer/payment/create")
    public CommonResult create(@RequestBody Payment payment){
        return restTemplate.postForObject(PaymentSrv_URL+"payment/create", payment, CommonResult.class);

    }
}
