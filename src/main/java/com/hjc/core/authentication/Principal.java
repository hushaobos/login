package com.hjc.core.authentication;

import java.util.Map;

public interface Principal {
    long getId();

    int getRole();

    String getNickname();

    int getGrade();

    String getUsername();

    Map<String,Object> getAttributes();
}
