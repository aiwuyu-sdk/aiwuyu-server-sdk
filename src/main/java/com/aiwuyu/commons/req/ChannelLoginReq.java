/*
 * Copyright 2019 Aiwuyu.com All right reserved. This software is the confidential and proprietary information of
 * Aiwuyu.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with Aiwuyu.com.
 */
package com.aiwuyu.commons.req;


/**
 * <P>
 * 渠道登录请求
 * <P>
 * 
 * @author caojiayao
 * @version $Id: ChannelLoginReq.java, v 0.1 2019年4月16日 下午7:30:07 caojiayao Exp $
 */
public class ChannelLoginReq extends BaseChannelReq {

    /** 用户uid **/
    private String uid;
    
    /** 登录时间 **/
    private String loginDate;

    /**
     * @return the uid
     */
    public String getUid() {
        return uid;
    }

    /**
     * @param uid the uid to set
     */
    public void setUid(String uid) {
        this.uid = uid;
    }

    /**
     * @return the loginDate
     */
    public String getLoginDate() {
        return loginDate;
    }

    /**
     * @param loginDate the loginDate to set
     */
    public void setLoginDate(String loginDate) {
        this.loginDate = loginDate;
    }
    
    
}
