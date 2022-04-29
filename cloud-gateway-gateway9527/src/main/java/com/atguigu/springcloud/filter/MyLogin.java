package com.atguigu.springcloud.filter;

import com.atguigu.springcloud.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;

//@Component
@Slf4j
public class MyLogin implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest re = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        if (re.getURI().getPath().contains("/login")){
            log.info("{}","去登录");
            return chain.filter(exchange);
        }
        HttpHeaders headers = re.getHeaders();
        String token = headers.getFirst("token");
        //6. 判断请求头中是否有令牌
        if (StringUtils.isBlank(token)) {
            //7. 响应中放入返回的状态吗, 没有权限访问
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            //8. 返回
            return response.setComplete();
        }

        //9. 如果请求头中有令牌则解析令牌
        try {
//            JwtUtil.parseJWT(token);
            Boolean tokenExpired = JwtUtil.isTokenExpired(token);
            log.info("{}","token验证成功,是否被修改过tokenExpired："+tokenExpired);
            if (tokenExpired){//被修改过
                response.setStatusCode(HttpStatus.UNAUTHORIZED);
                return response.setComplete();
            }
        } catch (Exception e) {
            e.printStackTrace();
            //10. 解析jwt令牌出错, 说明令牌过期或者伪造等不合法情况出现
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            //11. 返回
            return response.setComplete();
        }
        //12. 放行
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
