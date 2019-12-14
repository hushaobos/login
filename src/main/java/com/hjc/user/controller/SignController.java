package com.hjc.user.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.hjc.http.resp.ResponseConstant;
import com.hjc.sign.core.common.Credential;
import com.hjc.sign.core.common.UserPwdCredential;
import com.hjc.sign.core.resolve.CompleteCredentailResolver;
import com.hjc.http.constant.HttpConstant;
import com.hjc.http.resp.DefaultObjectResponse;
import com.hjc.http.resp.RespResult;
import com.hjc.http.resp.Response;
import com.hjc.sign.service.LoginService;
import com.hjc.token.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * @author 胡绍波
 * user请求类
 */
@RestController
public class SignController {
    @Autowired
    private LoginService loginServiceImpl;

    @Autowired
    private CompleteCredentailResolver completeCredentailResolver;//解析登录的请求参数

    /**
     * 登录与处理接口
     * @return
     */
    @RequestMapping(value = HttpConstant.LOGIN,method = RequestMethod.GET)
    @Token(save = true)
    public Response login(){
        return new DefaultObjectResponse(RespResult.SUCCESS);
    }

    /**
     * 登录与处理接口
     * @return
     */
    @RequestMapping(value = HttpConstant.LOGIN,method = RequestMethod.POST)
    @Token(remove = true)
    public Response loginHandle(HttpServletRequest request, HttpServletResponse response, @RequestBody UserPwdCredential pwdCredential){
        Credential credential;
        if(Objects.isNull(pwdCredential)) {
            credential = completeCredentailResolver.paramResolve(request);
        }
        else {
            credential = pwdCredential;
        }

        if(credential == null)//如果credential为空则证明用户所传的信息为空
        {
            return ResponseConstant.LOGIN_EMPRT_RESULT;
        }
        else {
            return loginServiceImpl.auth(pwdCredential, response);
        }
    }
}
