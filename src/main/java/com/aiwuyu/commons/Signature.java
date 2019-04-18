package com.aiwuyu.commons;

import java.nio.charset.Charset;
import java.util.Map;

/**
 * <P> 签名接口 <P>
 * @author caojiayao 
 * @version $Id: Signature.java, v 0.1 2019年4月17日 下午8:21:39 caojiayao Exp $
 */
public interface Signature {

    /**
     * 算法名称
     * @return
     */
    String name();

    /**
     * <P> 加签 <P>
     * @param context
     * @param prikey
     * @param charset
     * @return
     * @throws Exception
     */
    String sign(final String context, final String prikey, final Charset charset) throws Exception;
    
    /**
     * <P> 加签 <P>
     * @param contextMap
     * @param prikey
     * @param charset
     * @return
     * @throws Exception
     */
    Map<String, Object> sign(final Map<String, Object> contextMap, final String prikey, final Charset charset) throws Exception;

    /**
     * 数据校验
     * @param text
     * @param sign
     * @param key
     * @param charset
     * @return
     */
    boolean verify(final String context, final String sign, final String pubkey,
                   final Charset charset) throws Exception;
    
    /**
     * 数据校验
     * @param text
     * @param sign
     * @param key
     * @param charset
     * @return
     */
    boolean verify(final Map<String, Object> contextMap, final String sign, final String pubkey,
                   final Charset charset) throws Exception;

}
