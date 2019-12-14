package com.hjc.core.authentication;

import com.hjc.core.authentication.handler.AuthenticationHandler;
import com.hjc.sign.core.common.Credential;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthenticationManagerImpl implements AuthenticationManager {
    @Autowired
    private List<AuthenticationHandler> list;

    @Override
    public Authentication authenticate(Credential credential) {
        boolean support = false;//判断认证处理器是否支持该凭据处理

        Authentication authentication = null;
        for (AuthenticationHandler authenticationHandler : list)
        {
            support = authenticationHandler.support(credential);//检查认证处理器是否支持该处理

            if(support)//如果支持则进行凭据认证处理
            {
                authentication = authenticationHandler.authenticate(credential);
                break;
            }
        }
        return authentication;
    }
}
