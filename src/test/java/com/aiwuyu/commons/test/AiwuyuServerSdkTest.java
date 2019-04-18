/*
 * Copyright 2019 Aiwuyu.com All right reserved. This software is the confidential and proprietary information of
 * Aiwuyu.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with Aiwuyu.com.
 */
package com.aiwuyu.commons.test;

import java.nio.charset.Charset;
import java.util.Date;
import java.util.UUID;

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

    String privateKey = "渠道私钥";

    @Test
    public void channelSign_test() {
        ChannelLoginReq channelLoginReq = new ChannelLoginReq();
        channelLoginReq.setReqDate(DateFormatUtils.format(new Date(), Constants.SHARE_DEFAULT_FORMAT));
        channelLoginReq.setUid(UUID.randomUUID().toString());
        channelLoginReq.setChannelCode("channel001");
        channelLoginReq.setUid("dsadsadas");

        final String channelSignJson =
            AiwuyuServerSdk.channelSign(channelLoginReq, "RSA", privateKey, Charset.defaultCharset());

        System.out.println(channelSignJson);
    }

}
