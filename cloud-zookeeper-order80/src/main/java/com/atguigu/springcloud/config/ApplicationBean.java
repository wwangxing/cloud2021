package com.atguigu.springcloud.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ApplicationBean {
    @Bean
    @LoadBalanced //开启负载均衡，因为提供端是集群，不加无法通过注册中心中的别名获取提供者地址
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }
}
