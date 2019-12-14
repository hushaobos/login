package com.hjc.user.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.hjc.user.po.User;
import com.hjc.user.dto.valid.UserValidDto;
import com.hjc.user.dto.query.UserQuery;
import com.hjc.user.vo.UserVo;
import com.hjc.user.dao.UserDao;
import com.hjc.user.service.UserService;
import com.hjc.http.resp.DefaultListResponse;
import com.hjc.http.resp.DefaultObjectResponse;
import com.hjc.http.resp.ObjectResponse;
import com.hjc.http.resp.ListResponse;
import com.hjc.http.resp.Response;
import com.hjc.http.resp.RespResult;
import com.hjc.http.resp.ResponseConstant;
import javax.annotation.Resource;
import com.hjc.redis.aspect.annotation.HjcCacheEvict;
import com.hjc.redis.aspect.annotation.HjcCachePut;
import com.hjc.redis.aspect.annotation.HjcCacheType;
import com.hjc.redis.aspect.annotation.HjcCacheable;
import java.util.Objects;
import java.util.List;

@Service(interfaceClass = UserService.class)
public class UserServiceImpl implements UserService {
	@Resource(name = "userDao")
	private UserDao userDao;

	@Override
	@HjcCacheable(value = "user_list",key = "#userQuery.user.userId",cacheType = HjcCacheType.STRING)
	public DefaultListResponse<User> queryUserList(UserQuery userQuery){
		List<UserVo> list = userDao.queryUserList(userQuery);
		return new DefaultListResponse(RespResult.SUCCESS,list,null);
	}

	@Override
	@HjcCacheable(value = "user",key = "#userQuery.user.userId",cacheType = HjcCacheType.STRING)
	public DefaultObjectResponse<User> queryUserById(UserQuery userQuery){
		if (Objects.nonNull(userQuery.getUser()) && Objects.nonNull(userQuery.getUser().getUserId())) {
			UserVo userVo = userDao.queryUserById(userQuery);
			return new DefaultObjectResponse(RespResult.SUCCESS,userVo);
		}
		return new DefaultObjectResponse(RespResult.SUCCESS);
	}

	@Override
	@HjcCachePut(value = "user",key = "#userValidDto.userId",cacheType = HjcCacheType.STRING)
	public ObjectResponse<User> addUser(UserValidDto userValidDto){
		int addNum = userDao.addUser(userValidDto);
		if (addNum == 0) {
			return ResponseConstant.INSERT_FAILED_RESULT;
		}
		return new DefaultObjectResponse(RespResult.SUCCESS,userValidDto);
	}

	@Override
	@HjcCacheEvict(value = "user",key = "#userValidDto.userId",cacheType = HjcCacheType.STRING)
	public Response updateUser(UserValidDto userValidDto) throws Exception {
		if (Objects.isNull(userValidDto.getUserId())) {
			return ResponseConstant.ID_NULL_RESULT;
		}
		try {
			int updateNum = userDao.updateUser(userValidDto);
			if (updateNum == 0) {
				return ResponseConstant.DATA_NOT_EXIST_RESULT;
			}
		}catch (Exception e){
			throw new Exception(ResponseConstant.UPDATE_FAILED_RESULT.toString());
		}
		return ResponseConstant.SUCCESS_RESULT;
	}

	@Override
	@HjcCacheEvict(value = "user",key = "#userId",cacheType = HjcCacheType.STRING)
	public Response deleteUser(Long userId) throws Exception {
		if (Objects.isNull(userId) || userId == 0) {
			return ResponseConstant.ID_NULL_RESULT;
		}
		try {
			int deleteNum = userDao.deleteUser(userId);
			if (deleteNum == 0) {
				return ResponseConstant.DATA_NOT_EXIST_RESULT;
			}
		}catch (Exception e){
			throw new Exception(ResponseConstant.UPDATE_FAILED_RESULT.toString());
		}
		return ResponseConstant.SUCCESS_RESULT;
	}
}