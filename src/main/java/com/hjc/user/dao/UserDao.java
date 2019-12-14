package com.hjc.user.dao;

import com.hjc.user.vo.UserVo;
import com.hjc.user.dto.valid.UserValidDto;
import com.hjc.user.dto.query.UserQuery;
import java.util.List;

public interface UserDao{
	 List<UserVo> queryUserList(UserQuery userQuery);

	 UserVo queryUserById(UserQuery userQuery);

	 int addUser(UserValidDto userValidDto);

	 int updateUser(UserValidDto userValidDto);

	 int deleteUser(Long userId);
}