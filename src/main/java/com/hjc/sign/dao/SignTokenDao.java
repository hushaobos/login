package com.hjc.sign.dao;

import com.hjc.sign.vo.SignTokenVo;
import com.hjc.sign.dto.valid.SignTokenValidDto;
import com.hjc.sign.dto.query.SignTokenQuery;
import java.util.List;

public interface SignTokenDao{
	 List<SignTokenVo> querySignTokenList(SignTokenQuery signTokenQuery);

	 SignTokenVo querySignTokenById(SignTokenQuery signTokenQuery);

	 int addSignToken(SignTokenValidDto signTokenValidDto);

	 int updateSignToken(SignTokenValidDto signTokenValidDto);

	 int deleteSignToken(Long sTokenid);
}