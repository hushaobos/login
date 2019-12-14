package com.hjc.sign.service;

import com.hjc.sign.core.common.Credential;
import com.hjc.sign.core.common.UserPwdCredential;
import com.hjc.http.resp.Response;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface LoginService{
    Response auth(Credential credential, HttpServletResponse response);
}
