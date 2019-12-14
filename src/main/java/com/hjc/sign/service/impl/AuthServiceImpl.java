package com.hjc.sign.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.hjc.http.resp.Response;
import com.hjc.sign.core.common.Credential;
import com.hjc.sign.service.AuthService;
import com.hjc.sign.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;

@Service(interfaceClass = AuthService.class)
public class AuthServiceImpl implements AuthService{
    @Autowired
    private LoginService loginServiceImpl;

    @Override
    public Response auth(Credential credential) {
        return loginServiceImpl.auth(credential,null);
    }
}
