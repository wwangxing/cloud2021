package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.service.PaymentFeignService;
import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.UUID;

@RestController
@Slf4j
@DefaultProperties(defaultFallback = "globleHandler")
public class OrderController {

    @Resource
    private PaymentFeignService paymentFeignService;

    @GetMapping("/consumer/payment/ok/{id}")
    public String paymentInfo_OK(@PathVariable("id") Integer id)
    {
        return paymentFeignService.paymentInfo_OK(id);
    }
    @GetMapping("/consumer/payment/timeout/{id}")
    @HystrixCommand(commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.interruptOnTimeout",value = "1500")
    })
    public String paymentInfo_TimeOut(@PathVariable("id") Integer id)
    {
        return paymentFeignService.paymentInfo_TimeOut(id);
    }
    @GetMapping("/consumer/payment/circuit/{id}")
//    @HystrixCommand
    public String paymentCircuitBreaker(@PathVariable("id")Integer id){
        return paymentFeignService.paymentCircuitBreaker(id);
    }

    @GetMapping("/consumer/payment/flowlimit/{id}")
//    @HystrixCommand
    //服务限流  threadPoolKey给线程池起个别名
    @HystrixCommand(fallbackMethod = "paymentFlowlimitHandler",threadPoolKey = "hello",threadPoolProperties = {
            // 该参数用来设置执行命令线程池的核心线程数，该值也就是命令执行的最大并发量
            @HystrixProperty(name = "coreSize", value = "20"),
            // 该参数用来设置线程池的最大队列大小。当设置为 -1 时，线程池将使用 SynchronousQueue 实现的队列，
            // 否则将使用 LinkedBlockingQueue 实现的队列。
            @HystrixProperty(name = "maxQueueSize", value = "1"),
            // 该参数用来为队列设置拒绝阈值。 通过该参数， 即使队列没有达到最大值也能拒绝请求。
            // 该参数主要是对 LinkedBlockingQueue 队列的补充,因为 LinkedBlockingQueue
            // 队列不能动态修改它的对象大小，而通过该属性就可以调整拒绝请求的队列大小了。
            @HystrixProperty(name = "queueSizeRejectionThreshold", value = "5"),
    })
    public String paymentFlowlimit(@PathVariable("id") Integer id){
        return paymentFeignService.paymentFlowlimit(id);
    }

    public String paymentFlowlimitHandler(Integer id) {
        return "consumer限流了，请稍后再试，/(ㄒoㄒ)/~~   id: " +id;
    }

    public String globleHandler(){
        return "*******come in globle fallback*********";
    }
}
