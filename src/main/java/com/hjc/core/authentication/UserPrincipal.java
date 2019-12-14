package com.hjc.core.authentication;

import java.io.Serializable;
import java.util.Map;

public class UserPrincipal extends AbstractPrincipal implements Serializable {
    private static final long serialVersionUID = -1L;

    public UserPrincipal() {
    }

    public UserPrincipal(long id, int role, String nickname, int grade, String username, Map<String, Object> attributes) {
        super(id, role, nickname, grade, username, attributes);
    }

    public UserPrincipal(long id, int role, String nickname, int grade, String email, String mobile, int accountType, String userToken, String username, Map<String, Object> attributes) {
        super(id, role, nickname, grade, email, mobile, accountType, userToken, username, attributes);
    }
}
