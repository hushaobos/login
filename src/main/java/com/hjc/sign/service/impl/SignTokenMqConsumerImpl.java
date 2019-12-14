package com.hjc.sign.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.hjc.mq.config.HjcRocketMqHandle;
import com.hjc.mq.constant.RocketMqTopicConstant;
import com.hjc.mq.constant.RocketTagConstant;
import com.hjc.sign.dto.valid.SignTokenValidDto;
import com.hjc.sign.po.SignToken;
import com.hjc.sign.service.SignTokenService;
import com.hjc.util.LoginConstant;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.Objects;

/**
 * token 处理类
 */
@Component
@HjcRocketMqHandle(topic = RocketMqTopicConstant.HJC_SIGN_TOPIC,tag = RocketTagConstant.TOKEN_ADD)
public class SignTokenMqConsumerImpl implements RocketMQListener<MessageExt> {
    @Reference(timeout = 5000, check= false)
    private SignTokenService signTokenService;

    @Override
    public void onMessage(MessageExt messageExt) {
        try {
            JSONObject object = JSONObject.parseObject(new String(messageExt.getBody(),"UTF-8"));
            SignTokenValidDto signToken = object.getObject(LoginConstant.SIGN_TOKEN,SignTokenValidDto.class.getGenericSuperclass());

            if(Objects.nonNull(signToken)){
                switch (messageExt.getTags()){
                    case RocketTagConstant
                            .TOKEN_ADD:
                        signTokenService.addSignToken(signToken);//添加token信息
                        break;
                }
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
