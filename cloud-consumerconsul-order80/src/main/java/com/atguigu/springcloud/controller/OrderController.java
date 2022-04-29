package com.atguigu.springcloud.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@RestController
@Slf4j
public class OrderController {

    @Resource
    private RestTemplate restTemplate;

    private static final String SERVER_URL = "http://consul-providor-server";

    @GetMapping("/consumer/payment/port")
    public String getPort(){
        return restTemplate.getForObject(SERVER_URL+"/payment/port", String.class);
    }
}
