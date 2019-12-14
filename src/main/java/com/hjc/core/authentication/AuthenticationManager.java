package com.hjc.core.authentication;


import com.hjc.sign.core.common.Credential;

public interface AuthenticationManager {
    Authentication authenticate(Credential credential);
}
