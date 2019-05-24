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
    String privateKey = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCi2pFZiVy0ey9HX6X4+Hx8lVAIphu6nYLPOkYpG3mApKZsKyCoaezdQc9inuNENmFfDC66s9QWPj6vnjMgf2kQt2FULK1IbDaY6n+9Y6UejuQJhY+oy1p3VPPn6kTsVmm945V3kkvAHLyhAlxUA0oOQw99myOJevuDrCC79OFQ6p5t6ICr5i0kH1tkRt/E5Y7oH6dLzb9kDFQFUZZ6va71P6gEmbbXoyW8EAFlMRO2W0dBoI8xUqZxM+vpMhyh77nqTGYW20Kt6aeJAkfRGAhYkhiJzf0rMFo2U2hIqKVNT05HlJcBLydOyAefJxX2IM4csNkL/REX/DCMssy9dLNbAgMBAAECggEAAKnZS78WhJm34JeQKoqAn1VQZSigFkuL++Il5Qb6oL6K+wfV8zmzQFeDS8iP7ng2P1fiXNpcs9Z/FOIVHT3HpMzKdbhU4GWod0kFSjU8t/onDa7gnfRuIuxSrhAzRa84cRCbTEhpTJgRYKqMy77Tr2kwh/d41peMrMWnwHxH6rd4S/v5xSxDu08eqZvy/MuZ+c3QHd5keYsF2oQZyBTK1F1wjhoDF7sdv5MsR7RaWUUsjMIM4e+T0u00J5v2+YvIWbG11Z3blHz3fS81Hb5LXh9w9D1VQiBAlb0CkDivrlQaLto7zVEIwCnK9oK8L0gjdLopeIvpV0X9wonFoawvEQKBgQDxA5O7L15ujmd62e8GcHhV1OAlGfh5NX04DK9VM80YX+rHZO34pwP5WWdahFp/XbIEfnyD9aqs8IIqpcmaihyVvZ1oGeG8tBc4/Mp9VZBlY02PpHNMUklpTO4fnlaQP4M269U8LJiCFwBRJW9X1SQc3VMzVsZfhS8BLoFrnoayUwKBgQCs+tlSSa4wIdiA643ttajjWmblQaUBNcCWLl7agstHz49w4j5A1VXYTplzOHDlPzsr0PKH4/bToE7roUGYnVStyOj44XbwEjoBGXPoQKY2NqZCQCpF+u3uC1eSRWg6btgQsK5tep5AWrwWEIoDJXihE6CSz7GHNXqyN1SxO97p2QKBgQDPshJ0ZAzP315jqz1l4jSBWxLSRkAvTB4d2CiclyhukfIEZQECoAM3tYjwo1BNpFPNtabeHS45HIY/9GD5EX4yuJXWaT90XAoLLSXevobSKLJlUAWgRuVGnmoyy0OzyVftU2yOD6xZpzK5uzXpjh8x1LVnb6dCF8b7ELWtJY7USwKBgGva0OxE8q4iPFtOBCXGUfLIaEXj411YxacebJg0W0mAOoD5IDXOjwMbJwS3mXTnMx7D3qUqV7lmSC8VUdA1sRnWnqSn+EACLVaIJ0QWI0zEUHAYkAMoTXhSbBzQ2AcvG9t0Pp4WiVu493OOhm8lywwsL7+6bofOqZ07cLw9h2UpAoGANJqbcfzZxwAQipPqfLK4wuK8nEIquqo4grhNvyeK4ehbh8TnMHy3F6uLd7kzbvBiYFoRMtu0gcNHXCN2bXmhZdWIRdPsJqdBapW+r7Lu2ZrV6Bkawdg7ZcdQ33yHQP14cC9Tz66r32/T7agiYl/Y+t8VLLLScUWsyJhlnst8NOs=";
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
