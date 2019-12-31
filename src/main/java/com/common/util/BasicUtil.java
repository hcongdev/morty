package com.common.util;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 基本工具类
 */
public class BasicUtil {

    /**
     * 将请求的request的参数重新组装。主要是将空值的替换成null,因为requestMap空值是"",这样处理有利于外部判断,
     * 同时将获取到的值映射到页面上
     *
     * @param request
     *            HttpServletRequest对象
     * @return 返回处理过后的数据
     */
    public static Map<String, Object> assemblyRequestMap() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        Map<String, Object> params = new HashMap<String, Object>();
        Map<String, String[]> map = request.getParameterMap();
        Iterator<String> key = map.keySet().iterator();
        while (key.hasNext()) {
            String k = (String) key.next().replace(".", "_");
            String[] value = null;
            if(!ObjectUtil.isNull(map.get(k))) {
                try {
                    value = map.get(k);
                } catch (java.lang.ClassCastException e) {
                    value = new String[]{map.get(k)+""};
                }
            }


            if (value == null) {
                params.put(k, null);
                request.setAttribute(k, null);
            } else if (value.length == 1) {
                String temp = null;
                if (!StringUtils.isEmpty(value[0])) {
                    temp = value[0];
                }
                params.put(k, temp);
                request.setAttribute(k, temp);
            } else if (value.length == 0) {
                params.put(k, null);
                request.setAttribute(k, null);
            } else if (value.length > 1) {
                params.put(k, value);
                request.setAttribute(k, value);
            }
        }
        return params;
    }
}
