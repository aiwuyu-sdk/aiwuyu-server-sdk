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

    String privateKey = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAOa+ufCJYPp/FFeV"
        + "Ylf7SfeB3k+Vc0kaqMnEnEQf6YMz8zw6tRYncA/PCxu+zNoSsedJbATp1RBS0UhY"
        + "T9XaqAPvqCcrSvJfeRjyjVDI9vWKDj6sy45zcVxBisytzLt6P/bTGdSL/L7Hcrs4"
        + "vZkBm2zHXa9ytYjol6nrVoTL+YjdAgMBAAECgYEAzKNVsDeGJGJTniFD6JdZ0AID"
        + "MT1v0uOr0dVmaRyMMen+y+mc9BrhzYNnbKHYd4ag9GgvPmRKq4FnTevUUerrmXLm"
        + "Pb6pT1q/znExpNvGzvzj/9xDrIic7LCtglHR8QijczNL/lqyt1DMeOF6w7gBDR7H"
        + "SAbiHwiYc6op0WlJvtkCQQD3SIA5luAt1FsFyQBMi4Mht/lYKwAkHZM+dnwEGxkz"
        + "IYZh5uGxBuKJ0GhNH97D/1v/oxGN7ED1Ts4gihXO3nQTAkEA7uD73kiZutSmK9bd"
        + "tODgh+nB7vtEnu/nAjTVb5jVKAeUl10H8zB8CggUJ/NvMWNZDyss4/CfZ0sYTIOj"
        + "Ky5NTwJBAJI9uEdWqQGxgpsrQhgCsexxBu9TjuPM8dWQ8Mrk08RfYT/o8TRmY/co"
        + "32YtIOs8y8WlRK5wOAJbNqAbycXr3S0CQQDDDVB8A5oLVSHOgCcOT+Tm9beWNPOm"
        + "N8vQIGX7NrcudWoeYidgqsFNy6TxrjOoBzAsU5vXD56JUGcp6v7r/mW/AkAWawKv"
        + "KGPJxDgq48X8bmJNLV0YFOwjcpDdQ6/GQHYuVEfyQ7IgkhJDWn/T6IaZ4uIbhAg3" + "m3wn0j+Ss0eTXv+Q";

    String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDmvrnwiWD6fxRXlWJX+0n3gd5P"
        + "lXNJGqjJxJxEH+mDM/M8OrUWJ3APzwsbvszaErHnSWwE6dUQUtFIWE/V2qgD76gn"
        + "K0ryX3kY8o1QyPb1ig4+rMuOc3FcQYrMrcy7ej/20xnUi/y+x3K7OL2ZAZtsx12v" + "crWI6Jep61aEy/mI3QIDAQAB";

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
