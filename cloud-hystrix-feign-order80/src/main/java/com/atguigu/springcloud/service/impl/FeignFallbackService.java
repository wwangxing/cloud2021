package com.atguigu.springcloud.service.impl;

import com.atguigu.springcloud.service.PaymentFeignService;
import org.springframework.stereotype.Component;

@Component
public class FeignFallbackService implements PaymentFeignService {

    @Override
    public String paymentInfo_OK(Integer id) {
        return "**************paymentInfo_OK****id:"+id;
    }

    @Override
    public String paymentInfo_TimeOut(Integer id) {
        return "**************paymentInfo_TimeOut****id:"+id;
    }

    @Override
    public String paymentCircuitBreaker(Integer id) {
        return "**************paymentCircuitBreaker****id:"+id;
    }

    @Override
    public String paymentFlowlimit(Integer id) {
        return "**************paymentFlowlimit****id:"+id;
    }
}
