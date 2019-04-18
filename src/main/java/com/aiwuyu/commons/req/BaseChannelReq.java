/*
 * Copyright 2019 Aiwuyu.com All right reserved. This software is the confidential and proprietary information of
 * Aiwuyu.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with Aiwuyu.com.
 */
package com.aiwuyu.commons.req;

import java.util.UUID;

/**
 * 
 * <P>
 * 请求渠道
 * <P>
 * 
 * @author caojiayao
 * @version $Id: BaseChannelReq.java, v 0.1 2019年4月17日 下午8:53:59 caojiayao Exp $
 */
public class BaseChannelReq {

    /** 请求时间 **/
    private String reqDate;

    /** 请求流水(每次请求不同的流水号, 最好使用uuid) **/
    private String reqNo = UUID.randomUUID().toString();

    /** 商户信息 **/
    private String merchantCode;

    /** 渠道信息 **/
    private String channelCode;

    /** 签名类型 **/
    private String signType;

    /** 签名 **/
    private String sign;

    /**
     * @return the reqDate
     */
    public String getReqDate() {
        return reqDate;
    }

    /**
     * @param reqDate the reqDate to set
     */
    public void setReqDate(String reqDate) {
        this.reqDate = reqDate;
    }

    /**
     * @return the reqNo
     */
    public String getReqNo() {
        return reqNo;
    }

    /**
     * @param reqNo the reqNo to set
     */
    public void setReqNo(String reqNo) {
        this.reqNo = reqNo;
    }

    /**
     * @return the merchantCode
     */
    public String getMerchantCode() {
        return merchantCode;
    }

    /**
     * @param merchantCode the merchantCode to set
     */
    public void setMerchantCode(String merchantCode) {
        this.merchantCode = merchantCode;
    }

    /**
     * @return the channelCode
     */
    public String getChannelCode() {
        return channelCode;
    }

    /**
     * @param channelCode the channelCode to set
     */
    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode;
    }

    /**
     * @return the signType
     */
    public String getSignType() {
        return signType;
    }

    /**
     * @param signType the signType to set
     */
    public void setSignType(String signType) {
        this.signType = signType;
    }

    /**
     * @return the sign
     */
    public String getSign() {
        return sign;
    }

    /**
     * @param sign the sign to set
     */
    public void setSign(String sign) {
        this.sign = sign;
    }
    
}
