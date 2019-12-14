package com.hjc.sign.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.hjc.sign.po.SignToken;
import com.hjc.sign.dto.valid.SignTokenValidDto;
import com.hjc.sign.dto.query.SignTokenQuery;
import com.hjc.sign.vo.SignTokenVo;
import com.hjc.sign.dao.SignTokenDao;
import com.hjc.sign.service.SignTokenService;
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

@Service(interfaceClass = SignTokenService.class)
public class SignTokenServiceImpl implements SignTokenService {
	@Resource(name = "signTokenDao")
	private SignTokenDao signTokenDao;

	@Override
	@HjcCacheable(value = "sign_list",key = "#signTokenQuery.signToken.sTokenid",cacheType = HjcCacheType.STRING)
	public DefaultListResponse<SignToken> querySignTokenList(SignTokenQuery signTokenQuery){
		List<SignTokenVo> list = signTokenDao.querySignTokenList(signTokenQuery);
		return new DefaultListResponse(RespResult.SUCCESS,list,null);
	}

	@Override
	@HjcCacheable(value = "sign",key = "#signTokenQuery.signToken.sTokenid",cacheType = HjcCacheType.STRING)
	public DefaultObjectResponse<SignToken> querySignTokenById(SignTokenQuery signTokenQuery){
		if (Objects.nonNull(signTokenQuery.getSignToken()) && Objects.nonNull(signTokenQuery.getSignToken().getSTokenid())) {
			SignTokenVo signTokenVo = signTokenDao.querySignTokenById(signTokenQuery);
			return new DefaultObjectResponse(RespResult.SUCCESS,signTokenVo);
		}
		return new DefaultObjectResponse(RespResult.SUCCESS);
	}

	@Override
	@HjcCachePut(value = "sign",key = "#signTokenValidDto.sTokenid",cacheType = HjcCacheType.STRING)
	public ObjectResponse<SignToken> addSignToken(SignTokenValidDto signTokenValidDto){
		int addNum = signTokenDao.addSignToken(signTokenValidDto);
		if (addNum == 0) {
			return ResponseConstant.INSERT_FAILED_RESULT;
		}
		return new DefaultObjectResponse(RespResult.SUCCESS,signTokenValidDto);
	}

	@Override
	@HjcCacheEvict(value = "sign",key = "#signTokenValidDto.sTokenid",cacheType = HjcCacheType.STRING)
	public Response updateSignToken(SignTokenValidDto signTokenValidDto) throws Exception {
		if (Objects.isNull(signTokenValidDto.getSTokenid())) {
			return ResponseConstant.ID_NULL_RESULT;
		}
		try {
			int updateNum = signTokenDao.updateSignToken(signTokenValidDto);
			if (updateNum == 0) {
				return ResponseConstant.DATA_NOT_EXIST_RESULT;
			}
		}catch (Exception e){
			throw new Exception(ResponseConstant.UPDATE_FAILED_RESULT.toString());
		}
		return ResponseConstant.SUCCESS_RESULT;
	}

	@Override
	@HjcCacheEvict(value = "sign",key = "#sTokenid",cacheType = HjcCacheType.STRING)
	public Response deleteSignToken(Long sTokenid) throws Exception {
		if (Objects.isNull(sTokenid) || sTokenid == 0) {
			return ResponseConstant.ID_NULL_RESULT;
		}
		try {
			int deleteNum = signTokenDao.deleteSignToken(sTokenid);
			if (deleteNum == 0) {
				return ResponseConstant.DATA_NOT_EXIST_RESULT;
			}
		}catch (Exception e){
			throw new Exception(ResponseConstant.UPDATE_FAILED_RESULT.toString());
		}
		return ResponseConstant.SUCCESS_RESULT;
	}
}