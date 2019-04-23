/*
 * Copyright 2019 Aiwuyu.com All right reserved. This software is the confidential and proprietary information of
 * Aiwuyu.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with Aiwuyu.com.
 */
package com.aiwuyu.commons.sdk;

import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import com.aiwuyu.commons.Constants;
import com.aiwuyu.commons.req.BaseChannelReq;
import com.aiwuyu.commons.util.BeanUtil;
import com.aiwuyu.commons.util.SignUtil;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * <P>
 * <P>
 * 
 * @author caojiayao
 * @version $Id: AiwuyuServerSdk.java, v 0.1 2019年4月17日 下午8:58:02 caojiayao Exp $
 */
public class AiwuyuServerSdk {

    /**
     * <P>
     * 渠道加签
     * <P>
     * 
     * @param channelReq
     * @param signType
     * @param priKey
     * @param charset
     * @return
     */
    public static String channelSign(BaseChannelReq channelReq, String signType, String priKey, Charset charset) {
        Map<String, Object> channelReqMap = BeanUtil.beanToMap(channelReq);

        try {
            channelReqMap = SignUtil.byCode(signType).sign(channelReqMap, priKey, charset);

            return JsonObjectMapperFactory.getInstance().writeValueAsString(channelReqMap);

        } catch (Exception e) {
            throw new RuntimeException("渠道加签错误 channelReqMap:" + channelReqMap + " , priKey:" + priKey, e);
        }

    }

    /**
     * <P> 渠道签名验证 <P>
     * @param channelSignJson
     * @param pubkey
     * @param charset
     * @return
     */
    public static boolean verifySign(String channelSignJson, String pubkey, Charset charset) {
        try {
            @SuppressWarnings("unchecked")
            Map<String, Object> channelReqMap = JsonObjectMapperFactory.getInstance().readValue(channelSignJson,
                new HashMap<String, Object>().getClass());

            return SignUtil.byCode(channelReqMap.get("signType").toString()).verify(channelReqMap,
                channelReqMap.get("sign").toString(), pubkey, charset);

        } catch (Exception e) {
            throw new RuntimeException("渠道验签错误 channelSignJson:" + channelSignJson + " , pubkey:" + pubkey, e);
        }

    }

    /**
     * <P>
     * json对象映射工厂
     * <P>
     * 
     * @author caojiayao
     * @version $Id: JsonUtil.java, v 0.1 2017年12月13日 下午8:27:21 caojiayao Exp $
     */
    public static class JsonObjectMapperFactory {
        private static final String DATE_FORMAT = Constants.SHARE_DEFAULT_FORMAT;
        private static ObjectMapper om = new ObjectMapper();

        static {
            om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            om.setDateFormat(new SimpleDateFormat(DATE_FORMAT));
            om.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            om.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        }

        public static ObjectMapper getInstance() {
            return om;
        }
    }

}
