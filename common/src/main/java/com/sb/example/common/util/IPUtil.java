package com.sb.example.common.util;


import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;


/**
 * IP工具类
 * @author jack
 * @description 获取客户端ip
 * @date 2014年4月3日 下午4:30:27
 */
public class IPUtil {
    public static final String DEFAULT_LOCAL_IP = "127.0.0.1";

    public final static String getIpAddressWithoutCdnChain(final HttpServletRequest request) {
        String ip = getIpAddress(request);
        if (StringUtils.isNotEmpty(ip) && ip.contains(",")) {
            return ip.split(",")[0];
        }
        return ip;
    }


    public final static String getIpAddress(final HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("x-forwarded-for");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_REAL_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("http_client_ip");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }


        if (ip != null && ip.indexOf(",") != -1 && ip.length() > 40) {
            ip = ip.substring(0, ip.lastIndexOf(",") + 1).trim();
        }


        if ("0:0:0:0:0:0:0:1".equals(ip)) {
            ip = DEFAULT_LOCAL_IP;
        }
        return ip;
    }
}
