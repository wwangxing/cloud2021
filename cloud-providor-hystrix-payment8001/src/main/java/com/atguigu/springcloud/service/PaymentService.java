package com.atguigu.springcloud.service;

import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class PaymentService {
    /**
     * 正常访问，一切OK
     * @param id
     * @return
     */
    public String paymentInfo_OK(Integer id)
    {
        return "线程池:"+Thread.currentThread().getName()+"paymentInfo_OK,id: "+id+"\t"+"O(∩_∩)O";
    }

    /**
     * 超时访问，演示降级
     * @param id
     * @return
     */
    @HystrixCommand(fallbackMethod = "paymentInfo_TimeOutHandler",commandProperties = {
        @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "3000")
    })
    public String paymentInfo_TimeOut(Integer id)
    {
        int second = 5;
        try { TimeUnit.SECONDS.sleep(second); } catch (InterruptedException e) { e.printStackTrace(); }
        return "线程池:"+Thread.currentThread().getName()+"paymentInfo_TimeOut,id: "+id+"\t"+"O(∩_∩)O，耗费"+second+"秒";
//        int t = 10/0;
//        return "线程池:"+Thread.currentThread().getName()+"paymentInfo_TimeOut,id: "+id+"\t"+"O(∩_∩)O，";
    }
    public String paymentInfo_TimeOutHandler(Integer id)
    {
        return "线程池:"+Thread.currentThread().getName()+"paymentInfo_TimeOutHandler,id: "+id+"\t"+"o(╥﹏╥)o";
    }


    //服务熔断  默认熔断就是开启状态，默认规则是：10s的窗口期，请求量为20，错误率达到50%熔断从closed>open,
    // 在5秒的空闲窗口期后，会再次进行尝试调用该接口（处于half open半开状态），成功率上升从half open>closed，恢复调用链路
    @HystrixCommand(fallbackMethod = "paymentCircuitBreakerHandler",commandProperties = {
            @HystrixProperty(name = "circuitBreaker.enabled",value = "true"),
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold",value = "30"),
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds",value = "10"),
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage",value = "60")
    })
    public String paymentCircuitBreaker(Integer id){

        if (id < 0){
            throw new  RuntimeException("******id 不能负数");
        }
        String serialNumber = UUID.randomUUID().toString();
        return Thread.currentThread().getName()+"\t"+"调用成功，流水号: " + serialNumber;
    }
    public String paymentCircuitBreakerHandler(Integer id) {
        return "id 不能负数，请稍后再试，/(ㄒoㄒ)/~~   id: " +id;
    }

//    //服务限流  threadPoolKey给线程池起个别名
//    @HystrixCommand(fallbackMethod = "paymentFlowlimitHandler",threadPoolKey = "hello",threadPoolProperties = {
//            // 该参数用来设置执行命令线程池的核心线程数，该值也就是命令执行的最大并发量
//            @HystrixProperty(name = "coreSize", value = "20"),
//            // 该参数用来设置线程池的最大队列大小。当设置为 -1 时，线程池将使用 SynchronousQueue 实现的队列，
//            // 否则将使用 LinkedBlockingQueue 实现的队列。
//            @HystrixProperty(name = "maxQueueSize", value = "2"),
//            // 该参数用来为队列设置拒绝阈值。 通过该参数， 即使队列没有达到最大值也能拒绝请求。
//            // 该参数主要是对 LinkedBlockingQueue 队列的补充,因为 LinkedBlockingQueue
//            // 队列不能动态修改它的对象大小，而通过该属性就可以调整拒绝请求的队列大小了。
//            @HystrixProperty(name = "queueSizeRejectionThreshold", value = "10"),
//    })
    public String paymentFlowlimit(Integer id) {
        String serialNumber = UUID.randomUUID().toString();
        return Thread.currentThread().getName()+"\t"+"调用成功，流水号: " + serialNumber;
    }
    public String paymentFlowlimitHandler(Integer id) {
        return "限流了，请稍后再试，/(ㄒoㄒ)/~~   id: " +id;
    }

}
