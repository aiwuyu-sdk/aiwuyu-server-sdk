/*
 * Copyright 2019 Aiwuyu.com All right reserved. This software is the confidential and proprietary information of
 * Aiwuyu.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with Aiwuyu.com.
 */
package com.aiwuyu.commons.test;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.junit.Test;

import com.aiwuyu.commons.Constants;
import com.aiwuyu.commons.req.ChannelLoginReq;
import com.aiwuyu.commons.sdk.AiwuyuServerSdk;

/**
 * <P>
 * <P>
 * 
 * @author caojiayao
 * @version $Id: AiwuyuServerSdkTest.java, v 0.1 2019年4月18日 上午11:21:57 caojiayao Exp $
 */
public class AiwuyuServerSdkTest {
    /** 渠道私钥 **/
    String privateKey = "渠道私钥";
    /** 渠道公钥 **/
    String publicKey = "渠道公钥";
    /** 加签类型 **/
    String signType = "RSA";

    @Test
    public void channelSign_test() {
        ChannelLoginReq channelLoginReq = new ChannelLoginReq();
        // 日期格式 yyyy-MM-dd HH:mm:ss
        channelLoginReq.setReqDate(DateFormatUtils.format(new Date(), Constants.SHARE_DEFAULT_FORMAT));
        channelLoginReq.setChannelCode("渠道CODE");
        channelLoginReq.setUid("渠道用户ID");

        // 获得渠道签名
        final String channelSignJson =
            AiwuyuServerSdk.channelSign(channelLoginReq, signType, privateKey, StandardCharsets.UTF_8);

        System.out.println(channelSignJson);
    }

}
