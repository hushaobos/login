package com.hjc.core.authentication.handler;

import com.hjc.authentication.UsernameNullException;
import com.hjc.sign.core.common.AuthConstant;
import com.hjc.sign.core.common.UserPwdCredential;
import com.hjc.sign.core.common.encrypt.PasswordEncrypt;
import com.hjc.user.dao.UserDao;
import com.hjc.user.dto.query.UserQuery;
import com.hjc.user.po.User;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.util.Objects;

@Service
@Order(1)
public class UserPwdAuthenticationHandler extends AbstractUserPwdAuthenticationHandler<UserPwdCredential> {
    @Resource(name = "userDao")
    protected UserDao userDao;

    /**检查密码是否正确
     *
     * @param credential 凭据
     * @return
     */
    @Override
    protected boolean userPwdCheck(UserPwdCredential credential) {
        String login_password = credential.getPassword();
        String username = credential.getUsername();
        String password = null;

        User user = new User();
        user.setAccount(username);
        UserQuery userQuery = new UserQuery();
        userQuery.setUser(user);
        user = userDao.queryUserById(userQuery);//获取用户密码和盐值
        if(Objects.isNull(user)){
            throw new UsernameNullException("账号不存在!");
        }
        try {
            String salt = user.getSalt();
            password = user.getPassword();

            login_password = PasswordEncrypt.encryptSHS256(login_password, salt);//密码加密
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String client = String.valueOf(credential.getParam(AuthConstant.CLIENT_NAME));
        return verifyAccount(password, login_password, user, client);
    }

    /**验证密码正确性，然后生成验证信息
     *
     * @param pwd
     * @param loginPwd
     * @param user
     * @param client
     * @return
     */
    private boolean verifyAccount(String pwd,String loginPwd,User user,final String client)
    {
        boolean check = Objects.equals(pwd,loginPwd);//jdk1.8的类，用于对比密码是否一样

        if(check)//如果密码正确则设置用户的认证信息
        {
            postAuthentication(user,check,client);
        }
        return check;
    }
}
