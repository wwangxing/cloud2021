package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.service.FeignPaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Slf4j
public class OrderController {

    @Resource
    private FeignPaymentService feignPaymentService;

    @GetMapping("/consumer/payment/port")
    public String getPort(){
        return feignPaymentService.getPort();
    };
    @GetMapping(value = "/consumer/payment/get/{id}")
    public CommonResult getPaymentById(@PathVariable("id") Long id){
        return feignPaymentService.getPaymentById(id);
    };
    @GetMapping("/consumer/payment/timeout")
    public String timeout(){
        return feignPaymentService.timeout();
    }
}
