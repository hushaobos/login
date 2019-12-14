package com.hjc.core.authentication.handler;

import com.hjc.core.authentication.Authentication;
import com.hjc.sign.core.common.Credential;
import com.hjc.sign.core.common.EncryCredential;
import com.hjc.sign.core.common.EncryCredentialInfo;
import com.hjc.sign.core.common.encrypt.GenerateSecretTokenEncrypt;

public abstract class AbstractEncryAuthenticationHandler<T extends Credential> extends AbstractAuthenticationHandler<T> {
    private static final Class<EncryCredential> DEFAULT_SUPPORT_CLASS = EncryCredential.class;

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

    /**解密凭据
     *
     * @param credential
     * @return
     */
    protected EncryCredentialInfo decryptCredential(String credential,String client,EncryCredentialInfo accessTokenInfo)
    {
        GenerateSecretTokenEncrypt.decryptSecretAccessToken(credential,accessTokenInfo,client);//解密凭据
        return accessTokenInfo;
    }

    /**检查credential有效性
     *
     * @param credential
     * @return
     */
    public abstract Authentication credentialCheck(T credential);

    /**验证信息,并返回Authentication认证信息
     *
     * @param credential 凭据
     * @return
     */
    @Override
    protected Authentication doAuthentication(T credential) {
        return credentialCheck(credential);
    }
}
