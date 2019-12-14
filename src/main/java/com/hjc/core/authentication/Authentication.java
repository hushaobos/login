package com.hjc.core.authentication;

import com.hjc.sign.po.SignToken;

import java.util.Map;

public interface Authentication {
    boolean isAuthentication();

    long getAuthenticationTime();

    Principal getPrincipal();

//    String getAccessToken();
//
//    String getRefreshToken();
//
//    long getAccessTokenExpiredTime();
//
//    long getRefreshTokenExpiredTime();
    SignToken getSingToken();

    Map<String,Object> getAttributes();
}
