package com.hjc.test.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.hjc.test.service.TestService;
import com.hjc.user.po.User;

@Service
public class TestServiceImpl implements TestService {

    @Override
    public User getInfo() {
        User user = new User();
        user.setUserId(222L);
        return user;
    }
}
