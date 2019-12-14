package com.hjc.core.authentication.handler;


import com.hjc.core.authentication.Authentication;
import com.hjc.sign.core.common.Credential;

public interface AuthenticationHandler<T extends Credential> {
    boolean support(T credential);

    Authentication authenticate(T credential);
}
