package com.hjc.core.authentication;

import java.util.Map;

public abstract class AbstractPrincipal implements Principal{
    /**
     * 用户Id
     */
    private long id;

    /**
     * 用户角色
     */
    private int role;

    /**
     * 用户呢称
     */
    private String nickname;

    /**
     * 用户等级
     */
    private int grade;

    /**
     * 邮箱
     */
    private String email;

    private String mobile;

    private int accountType;

    private String userToken;

    /**
     * 用户名
     */
    private String username;

    public AbstractPrincipal() {
    }

    public AbstractPrincipal(long id, int role, String nickname, int grade, String username, Map<String, Object> attributes) {
        this.id = id;
        this.role = role;
        this.nickname = nickname;
        this.grade = grade;
        this.username = username;
        this.attributes = attributes;
    }

    public AbstractPrincipal(long id, int role, String nickname, int grade, String email, String mobile, int accountType, String userToken, String username, Map<String, Object> attributes) {
        this.id = id;
        this.role = role;
        this.nickname = nickname;
        this.grade = grade;
        this.email = email;
        this.mobile = mobile;
        this.accountType = accountType;
        this.userToken = userToken;
        this.username = username;
        this.attributes = attributes;
    }

    /**
     * 用户值
     */
    private Map<String,Object> attributes;

    @Override
    public long getId() {
        return this.id;
    }

    @Override
    public int getRole() {
        return this.role;
    }

    @Override
    public String getNickname() {
        return this.nickname;
    }

    @Override
    public int getGrade() {
        return 0;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return null;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getAccountType() {
        return accountType;
    }

    public void setAccountType(int accountType) {
        this.accountType = accountType;
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }
}
