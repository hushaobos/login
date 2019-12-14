package com.hjc.core.authentication.handler;

import com.hjc.authentication.PasswordIncorrectException;
import com.hjc.core.authentication.Authentication;
import com.hjc.sign.core.common.Credential;
import com.hjc.sign.core.common.UserPwdCredential;

public abstract class AbstractUserPwdAuthenticationHandler<T extends Credential> extends AbstractAuthenticationHandler<T> {
    private static final Class<UserPwdCredential> DEFAULT_SUPPORT_CLASS = UserPwdCredential.class;

    private Class<?> SUPPORT = DEFAULT_SUPPORT_CLASS;

    /**检查是否支持该凭据的处理
     *
     * @param credential
     * @return
     */
    @Override
    public boolean support(final T credential) {
        return credential != null && SUPPORT.equals(credential.getClass()) ? true : false;
    }

    /**检查用户名和密码是否正确
     *
     * @param credential 凭据
     * @return
     */
    protected abstract boolean userPwdCheck(final T credential);


    /**验证信息,并返回Authentication认证信息
     *
     * @param credential 凭据
     * @return
     */
    @Override
    protected Authentication doAuthentication(final T credential)
    {
        boolean check = userPwdCheck(credential);//检查用户名和密码是否正确

        if(!check) {//密码不正确则抛出异常
            throw PasswordIncorrectException.PWD_INCORRECT;
        }

        return getAuthentication();
    }
}
