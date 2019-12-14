package com.hjc.sign.dao;

import com.hjc.sign.vo.SignRecordVo;
import com.hjc.sign.dto.valid.SignRecordValidDto;
import com.hjc.sign.dto.query.SignRecordQuery;
import java.util.List;

public interface SignRecordDao{
	 List<SignRecordVo> querySignRecordList(SignRecordQuery signRecordQuery);

	 SignRecordVo querySignRecordById(SignRecordQuery signRecordQuery);

	 int addSignRecord(SignRecordValidDto signRecordValidDto);

	 int updateSignRecord(SignRecordValidDto signRecordValidDto);

	 int deleteSignRecord(Long srecordId);
}