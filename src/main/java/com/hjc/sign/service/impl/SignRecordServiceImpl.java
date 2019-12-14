package com.hjc.sign.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.hjc.sign.po.SignRecord;
import com.hjc.sign.dto.valid.SignRecordValidDto;
import com.hjc.sign.dto.query.SignRecordQuery;
import com.hjc.sign.vo.SignRecordVo;
import com.hjc.sign.dao.SignRecordDao;
import com.hjc.sign.service.SignRecordService;
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

@Service(interfaceClass = SignRecordService.class)
public class SignRecordServiceImpl implements SignRecordService {
	@Resource(name = "signRecordDao")
	private SignRecordDao signRecordDao;

	@Override
	@HjcCacheable(value = "sign_list",key = "#signRecordQuery.signRecord.srecordId",cacheType = HjcCacheType.STRING)
	public DefaultListResponse<SignRecord> querySignRecordList(SignRecordQuery signRecordQuery){
		List<SignRecordVo> list = signRecordDao.querySignRecordList(signRecordQuery);
		return new DefaultListResponse(RespResult.SUCCESS,list,null);
	}

	@Override
	@HjcCacheable(value = "sign",key = "#signRecordQuery.signRecord.srecordId",cacheType = HjcCacheType.STRING)
	public DefaultObjectResponse<SignRecord> querySignRecordById(SignRecordQuery signRecordQuery){
		if (Objects.nonNull(signRecordQuery.getSignRecord()) && Objects.nonNull(signRecordQuery.getSignRecord().getSrecordId())) {
			SignRecordVo signRecordVo = signRecordDao.querySignRecordById(signRecordQuery);
			return new DefaultObjectResponse(RespResult.SUCCESS,signRecordVo);
		}
		return new DefaultObjectResponse(RespResult.SUCCESS);
	}

	@Override
	@HjcCachePut(value = "sign",key = "#signRecordValidDto.srecordId",cacheType = HjcCacheType.STRING)
	public ObjectResponse<SignRecord> addSignRecord(SignRecordValidDto signRecordValidDto){
		int addNum = signRecordDao.addSignRecord(signRecordValidDto);
		if (addNum == 0) {
			return ResponseConstant.INSERT_FAILED_RESULT;
		}
		return new DefaultObjectResponse(RespResult.SUCCESS,signRecordValidDto);
	}

	@Override
	@HjcCacheEvict(value = "sign",key = "#signRecordValidDto.srecordId",cacheType = HjcCacheType.STRING)
	public Response updateSignRecord(SignRecordValidDto signRecordValidDto) throws Exception {
		if (Objects.isNull(signRecordValidDto.getSrecordId())) {
			return ResponseConstant.ID_NULL_RESULT;
		}
		try {
			int updateNum = signRecordDao.updateSignRecord(signRecordValidDto);
			if (updateNum == 0) {
				return ResponseConstant.DATA_NOT_EXIST_RESULT;
			}
		}catch (Exception e){
			throw new Exception(ResponseConstant.UPDATE_FAILED_RESULT.toString());
		}
		return ResponseConstant.SUCCESS_RESULT;
	}

	@Override
	@HjcCacheEvict(value = "sign",key = "#srecordId",cacheType = HjcCacheType.STRING)
	public Response deleteSignRecord(Long srecordId) throws Exception {
		if (Objects.isNull(srecordId) || srecordId == 0) {
			return ResponseConstant.ID_NULL_RESULT;
		}
		try {
			int deleteNum = signRecordDao.deleteSignRecord(srecordId);
			if (deleteNum == 0) {
				return ResponseConstant.DATA_NOT_EXIST_RESULT;
			}
		}catch (Exception e){
			throw new Exception(ResponseConstant.UPDATE_FAILED_RESULT.toString());
		}
		return ResponseConstant.SUCCESS_RESULT;
	}
}