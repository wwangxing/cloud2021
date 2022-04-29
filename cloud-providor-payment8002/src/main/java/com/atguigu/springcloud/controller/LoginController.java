package com.atguigu.springcloud.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.atguigu.springcloud.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 模拟登陆
 */
@RestController
@Slf4j
public class LoginController {
    @PostMapping("/login")
    public String login(String name,String password){
        JSONObject ob = new JSONObject();
        if (StringUtils.isNotBlank(name) && StringUtils.isNotBlank(password)){
            Map<String, Object> user = new HashMap<>();
            user.put("username", name);
            user.put("password", password);
            String token = JwtUtil.createJWT(name + password, JSON.toJSONString(user), 3600 * 24);
            ob.put("token", token);
        }
        return JSON.toJSONString(ob);
    }
}
