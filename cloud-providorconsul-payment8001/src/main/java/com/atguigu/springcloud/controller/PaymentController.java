package com.atguigu.springcloud.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class PaymentController {

    @Value("${server.port}")
    private String PORT;

    @GetMapping("/payment/port")
    public String getPort(){
        return "consul payment port:"+PORT;
    }
}

