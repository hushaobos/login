package com.hjc.test.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.hjc.http.resp.Response;
import com.hjc.http.resp.ResponseConstant;
import com.hjc.redis.aspect.annotation.HjcCacheEvict;
import com.hjc.redis.aspect.annotation.HjcCachePut;
import com.hjc.redis.aspect.annotation.HjcCacheType;
import com.hjc.redis.aspect.annotation.HjcCacheable;
import com.hjc.test.service.TestService;
import com.hjc.user.dto.query.UserQuery;
import com.hjc.user.dto.valid.UserValidDto;
import com.hjc.user.po.User;
import com.hjc.user.service.UserService;
import io.shardingsphere.shardingjdbc.jdbc.core.datasource.MasterSlaveDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class TestController {
    Logger logger = LoggerFactory.getLogger(getClass());
//    @Reference(timeout = 5000, check= false)
    @Autowired
    private UserService userServiceImpl;

    @RequestMapping("/test")
//    @HjcCacheable(value = "auth",key = "#user.userId",cacheType = HjcCacheType.STRING)
    public Response test(User user,UserQuery userQuery)
    {
        userQuery.setUser(user);
        return userServiceImpl.queryUserById(userQuery);
    }

    @RequestMapping("/testList")
//    @HjcCacheable(value = "auth",key = "#user.userId",cacheType = HjcCacheType.STRING)
    public Response testl(User user,UserQuery userQuery)
    {
        userQuery.setUser(user);
        return userServiceImpl.queryUserList(userQuery);
    }

    @RequestMapping("/testwrite")
//    @HjcCachePut(value = "auth",key = "#user.userId",cacheType = HjcCacheType.STRING)
    public Response testw(UserValidDto user)
    {
        user.setAccount("3232");
        user.setAreaCode("32323");
        user.setAvatar("2332");
        user.setEmail("ewewew");
        user.setIdentityCard("ewewew");
        user.setName("e33232");
        user.setNickName("323232");
        user.setPassword("33232");
        user.setPhone("23232");
        user.setRegister(new Date());
        user.setResidence("3232");
        user.setRole((short) 1);
        user.setSalt("wqwq");
        user.setSex(false);
        user.setState((short) 1);
        return userServiceImpl.addUser(user);
    }

    @RequestMapping("/testd")
//    @HjcCacheEvict(value = "auth",key = "#user.userId",cacheType = HjcCacheType.STRING)
    public Response testd(User user) throws Exception {
        return userServiceImpl.deleteUser(user.getUserId());
    }

    @RequestMapping("/nacos/serverlist")
    public void list(){

    }

}
