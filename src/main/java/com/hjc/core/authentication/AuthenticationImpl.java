package com.hjc.core.authentication;

import com.hjc.sign.po.SignToken;

import java.io.Serializable;
import java.util.Map;

public class AuthenticationImpl implements Authentication,Serializable {
    private static final long serialVersionUID = -1L;
    /**
     * 是否已认证
     */
    private boolean authentication;

    /**
     * 认证时间
     */
    private long authenticationTime;

    /**
     * 用户信息
     */
    private Principal principal;

    /**
     * token信息类
     */
    private SignToken singToken;

//    /**
//     * accessToken
//     */
//    private String accessToken;
//
//    private String refreshToken;//刷新令牌
//    private long accessTokenExpiredTime;//accessToken的有效期
//    private long refreshTokenExpiredTime;//refreshToken的有效期

    /**
     *客户端标识
     */
    private String client;

    /**
     * 认证信息
     */
    private Map<String,Object> attributes;

    @Override
    public boolean isAuthentication() {
        return authentication;
    }

    public void setAuthentication(boolean authentication) {
        this.authentication = authentication;
    }

    @Override
    public long getAuthenticationTime() {
        return authenticationTime;
    }

    @Override
    public Principal getPrincipal() {
        return principal;
    }

//    public void setAccessToken(String accessToken) {
//        this.accessToken = accessToken;
//    }
//
//    @Override
//    public String getAccessToken() {
//        return this.accessToken;
//    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public void setAuthenticationTime(long authenticationTime) {
        this.authenticationTime = authenticationTime;
    }

    public void setPrincipal(Principal principal) {
        this.principal = principal;
    }

    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

//    @Override
//    public String getRefreshToken() {
//        return refreshToken;
//    }
//
//    public void setRefreshToken(String refreshToken) {
//        this.refreshToken = refreshToken;
//    }
//
//    @Override
//    public long getAccessTokenExpiredTime() {
//        return accessTokenExpiredTime;
//    }
//
//    public void setAccessTokenExpiredTime(long accessTokenExpiredTime) {
//        this.accessTokenExpiredTime = accessTokenExpiredTime;
//    }
//
//    @Override
//    public long getRefreshTokenExpiredTime() {
//        return refreshTokenExpiredTime;
//    }
//
//    public void setRefreshTokenExpiredTime(long refreshTokenExpiredTime) {
//        this.refreshTokenExpiredTime = refreshTokenExpiredTime;
//    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    @Override
    public SignToken getSingToken() {
        return singToken;
    }

    public void setSingToken(SignToken singToken) {
        this.singToken = singToken;
    }
}
