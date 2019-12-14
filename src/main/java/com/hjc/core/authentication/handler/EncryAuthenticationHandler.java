package com.hjc.core.authentication.handler;

import com.hjc.core.authentication.Authentication;
import com.hjc.core.authentication.AuthenticationImpl;
import com.hjc.sign.core.common.AuthConstant;
import com.hjc.sign.core.common.EncryCredential;
import com.hjc.sign.core.common.EncryCredentialInfo;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

@Service
@Order(2)
public class EncryAuthenticationHandler extends AbstractEncryAuthenticationHandler<EncryCredential>{
    /**检查accesstoken是否过期
     *
     * @param expiryTime
     * @param nowTime
     * @param encryCredential
     * @return
     */
    private boolean authEffective(long expiryTime, long nowTime, EncryCredential encryCredential)
    {
        if(expiryTime > nowTime) {//认证时间过期时间当前时间
            return true;
        }

        String client = String.valueOf(encryCredential.getParam(AuthConstant.CLIENT_NAME));
        EncryCredentialInfo encryCredentialInfo = decryptCredential(encryCredential.getCredential(),client,encryCredential.getEncryCredentialInfo());//解密凭据信息
        encryCredential.setEncryCredentialInfo(encryCredentialInfo);
        if(encryCredentialInfo.getExpiryTime() >= nowTime) {//凭据有效时间大于当前时间
            return true;
        }
        return false;
    }

    /**检查凭据的有效期
     *
     * @param credential
     * @return
     */
    @Override
    public Authentication credentialCheck(EncryCredential credential) {
        String credentialStr = credential.getCredential();

        AuthenticationImpl authentication = getAuthentication(credential.getEncryCredentialInfo().getUserId());//从内存中查找认证信息

        logger.debug(" credentialStr "+credentialStr+" authentication "+authentication);

        if(authentication != null)
        {
            long nowTime = System.currentTimeMillis();
            long expiryTime = authentication.getAuthenticationTime() + effectiveTime;
            boolean effective = authEffective(expiryTime,nowTime,credential);
            if(effective)//检查有效则刷新有效期
            {
                authentication.setAuthenticationTime(nowTime);
                return authentication;
            }
        }
        return null;
    }
}
