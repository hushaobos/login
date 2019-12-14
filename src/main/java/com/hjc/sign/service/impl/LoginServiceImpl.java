package com.hjc.sign.service.impl;

import com.alibaba.csp.sentinel.util.StringUtil;
import com.hjc.authentication.PasswordIncorrectException;
import com.hjc.authentication.UsernameNullException;
import com.hjc.core.authentication.Authentication;
import com.hjc.core.authentication.AuthenticationManager;
import com.hjc.sign.core.common.AuthConstant;
import com.hjc.sign.core.common.Credential;
import com.hjc.sign.core.common.UserPwdCredential;
import com.hjc.sign.core.resolve.CompleteCredentailResolver;
import com.hjc.http.resp.*;
import com.hjc.http.utils.CookieUtils;
import com.hjc.sign.service.LoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

import static com.hjc.http.resp.RespResult.SUCCESS;

@Service
public class LoginServiceImpl implements LoginService {
    Logger logger = LoggerFactory.getLogger(LoginServiceImpl.class);

    @Value(value = "${login.default-redirect-url}")
    private String defaultUrl;

    @Value(value = "${hjc.domain}")
    private String domain;

    @Autowired
    private CompleteCredentailResolver completeCredentailResolver;//解析登录的请求参数

    @Autowired
    private AuthenticationManager authenticationManagerImpl;

    @Override
    public Response auth(Credential credential, HttpServletResponse response) {
        try {
            Authentication authentication = authenticationManagerImpl.authenticate(credential);//验证凭证

            if(Objects.isNull(authentication)){
                return ResponseConstant.UNAUTHORIZED_RESULT;
            }
            else if(Objects.nonNull(response)){
                return loginInfoInit(authentication,credential,response);
            }
            else{
                return ResponseConstant.SUCCESS_RESULT;
            }
        }
        catch (UsernameNullException e)//用户名不存在
        {
            return ResponseConstant.USERNAME_NOTEXIST_RESULT;
        }
        catch (PasswordIncorrectException e)//密码错误时报的错
        {
            return ResponseConstant.PASSWORD_INCORRECT_RESULT;//返回密码错误的状态码
        }
    }

    public Response loginInfoInit(Authentication authentication,Credential credential, HttpServletResponse response){
        Object redirectUrl = credential.getParam(AuthConstant.REDIRECT_PARAM_NAME);//从请求参数中取出重定向参数

        if(Objects.isNull(redirectUrl) || String.valueOf(redirectUrl).isEmpty())//redirectUrl没有值则设定默认的从定向地址
        {
            redirectUrl = defaultUrl;
        }

        Cookie cookie = CookieUtils.initCookie(AuthConstant.TOKEN_NAME,
                authentication.getSingToken().getScAccessToken(),
                AuthConstant.CK_ROOT_PATH,
                domain);//把accessToken存入cookie，并设置根目录、有效时间30分钟、domain为wlf.com

        Cookie cookieNK = CookieUtils.initCookie(AuthConstant.NICKNAME,
                authentication.getPrincipal().getNickname(),
                AuthConstant.CK_ROOT_PATH,
                domain);//把呢称存入cookie，并设置根目录、有效时间30分钟、domain为wlf.com

        Cookie id = CookieUtils.initCookie(AuthConstant.ID_NAME,
                String.valueOf(authentication.getPrincipal().getId()),
                AuthConstant.CK_ROOT_PATH,
                domain);//把呢称存入cookie，并设置根目录、有效时间30分钟、domain为wlf.com
        response.addCookie(cookie);
        response.addCookie(cookieNK);
        response.addCookie(id);

        return new DefaultObjectResponse<RedirectResponse>(SUCCESS,new RedirectResponse(String.valueOf(redirectUrl)));
    }
}
