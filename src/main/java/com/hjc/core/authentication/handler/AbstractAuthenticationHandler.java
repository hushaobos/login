package com.hjc.core.authentication.handler;

import com.hjc.core.authentication.Authentication;
import com.hjc.core.authentication.AuthenticationImpl;
import com.hjc.core.authentication.UserPrincipal;
import com.hjc.sign.core.common.AuthConstant;
import com.hjc.sign.core.common.Credential;
import com.hjc.sign.core.common.encrypt.GenerateSecretTokenEncrypt;
import com.hjc.redis.aspect.annotation.HjcCachePut;
import com.hjc.redis.aspect.annotation.HjcCacheType;
import com.hjc.redis.aspect.annotation.HjcCacheable;
import com.hjc.sign.po.SignToken;
import com.hjc.user.po.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractAuthenticationHandler<T extends Credential> implements AuthenticationHandler<T> {
    final Logger logger = LoggerFactory.getLogger(AbstractAuthenticationHandler.class);
    protected static final long effectiveTime = 30 * 60 * 1000;//accessToken有效时间为30分钟
    private AuthenticationImpl authentication;//认证信息


    @HjcCacheable(value = "user_auth",key = "#userId != null ? #userId : ''",cacheTime = effectiveTime, unless = "#userId == null",cacheType = HjcCacheType.HASH)
    public AuthenticationImpl getAuthentication(Long userId) {
        return authentication;
    }

    public AuthenticationImpl getAuthentication(){
        return authentication;
    }

    public void setAuthentication(AuthenticationImpl authentication) {
        this.authentication = authentication;
    }

    @Override
    public boolean support(T credential) {
        return false;
    }

    /**验证信息,并返回Authentication认证信息
     *
     * @param credential 凭据
     * @return
     */
    protected abstract Authentication doAuthentication(final T credential);

    /**设置authentication的值
     * @param user
     * @param isAuthenticate
     */
    protected void postAuthentication(User user, boolean isAuthenticate,final String client)
    {
        long userId = user.getUserId();
        Map<String,Object> attributes = new HashMap<String, Object>();
        UserPrincipal principal = new UserPrincipal(userId,
                user.getRole(),
                user.getNickName(),
                0,user.getEmail(),
                user.getPhone(),0,
                "",
                user.getAccount(),attributes);//用户信息存储类

        Date now = new Date();
        long nowTime = now.getTime();
        AuthenticationImpl authenticationImpl = new AuthenticationImpl();//用户认证信息存储类
        authenticationImpl.setAuthentication(isAuthenticate);
        authenticationImpl.setAuthenticationTime(nowTime);
        authenticationImpl.setPrincipal(principal);

        switch (client){
            case AuthConstant.APP_CLIENT:
                nowTime = nowTime / 1000 + GenerateSecretTokenEncrypt.APP_TERM;//appToken 期限30天，去掉毫秒数
                break;
            case AuthConstant.WEB_CLIENT:
            default:
                nowTime = nowTime / 1000 + GenerateSecretTokenEncrypt.WEB_TERM;//webToken 期限30分钟，去掉毫秒数
                break;
        }
        Date expiredDate = new Date(nowTime);

        String content = GenerateSecretTokenEncrypt.generateToken(userId,nowTime);//生成token的基本信息
        String accessToken = GenerateSecretTokenEncrypt.createAccessToken(content);//生成accessToken
        String refreshToken = GenerateSecretTokenEncrypt.createRefreshToken(content);//生成refreshToken

        SignToken signToken = new SignToken();
        signToken.setRefeshToken(refreshToken);
        signToken.setScAccessToken(accessToken);
        signToken.setAccessTokenCreateTime(now);
        signToken.setRefeshTokenCreateTime(now);
        signToken.setAccessTokenExpiredTime(expiredDate);
        signToken.setRefeshTokenCreateTime(expiredDate);
        signToken.setUserId(userId);

        authenticationImpl.setSingToken(signToken);
        authenticationImpl.setClient(client);

        Map<String,Object> authenticationAttributes = new HashMap<String, Object>();
        authenticationImpl.setAttributes(authenticationAttributes);

        setAuthentication(authenticationImpl);
    }

    /**进行凭据认证
     *
     * @param credential
     * @return
     */
    @Override
    @HjcCachePut(value = "user_auth", key = "#root != null ? #root.principal.id : ''",cacheTime = effectiveTime, unless = "#root == null",cacheType = HjcCacheType.HASH)
    public Authentication authenticate(T credential) {
        return doAuthentication(credential);//验证信息,并返回Authentication认证信息
    }
}
