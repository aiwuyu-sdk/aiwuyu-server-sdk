package com.aiwuyu.commons.util;

import static java.beans.Introspector.getBeanInfo;

import java.beans.PropertyDescriptor;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * <P> 转换工具 <P>
 * @author caojiayao 
 * @version $Id: BeanUtil.java, v 0.1 2019年4月18日 上午10:27:59 caojiayao Exp $
 */
public class BeanUtil {

    /**
     * <P>
     * 是否可以bean -> map
     * <P>
     * @param bean
     * @return
     */
    public static boolean isBeanToMap(Object bean) {
        return bean != null && !ClassUtils.isPrimitiveOrWrapper(bean.getClass()) && bean.getClass() != BigDecimal.class
            && !bean.getClass().isEnum() && bean.getClass() != Date.class && bean.getClass() != String.class;
    }

    /**
     * JavaBean转换为Map
     * 
     * @param bean
     * @return
     * @throws Exception
     */
    public static Map<String, Object> beanToMap(Object bean) {

        Map<String, Object> map = new HashMap<>();
        try {
            // 获取所有的属性描述器
            final PropertyDescriptor[] pds = getBeanInfo(bean.getClass()).getPropertyDescriptors();
            for (PropertyDescriptor pd : pds) {
                if ("class".equals(pd.getName())) {
                    continue;
                }
                map.put(pd.getName(), pd.getReadMethod().invoke(bean));
            }
        } catch (Exception e) {
            throw new RuntimeException("转换错误", e);
        }
        return map;
    }

    /**
     * <P>
     * 对象toString
     * <P>
     * 
     * @param value
     * @return
     */
    public static String toString(Object value) {
        return toString(value, StringUtils.EMPTY);
    }

    /**
     * <P>
     * 对象toString
     * <P>
     * 
     * @param value
     * @return
     */
    public static String toString(Object value, String defaultStr) {
        return value != null ? value.toString() : defaultStr;
    }
}
