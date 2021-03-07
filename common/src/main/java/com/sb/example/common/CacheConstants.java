package com.sb.example.common;

/**
 * 缓存常量类
 * Created by lwnull2018@gmail.com ON 2021/2/23.
 */
public class CacheConstants {


    /**
     * 缓存分钟数，1分钟
     */
    public static final int CACHE_ONE_MINS = 1;

    /**
     * 缓存分钟数，5分钟
     */
    public static final int CACHE_FIVE_MINS = 5;

    /**
     * 缓存小时数，1小时
     */
    public static final int CACHE_ONE_HOURS = 1;

    /**
     * 缓存天数，1天
     */
    public static final int CACHE_ONE_DAYS = 1;

    /**
     * redis中存储商户对象的主键格式，用法如下：
     * String.format(MERCH_ID_CACHE_KEY , id)
     */
    public static final String MERCH_ID_CACHE_KEY = "merch:%s";

    /**
     * redis中存储ip白名单的主键格式，用法如下：
     * String.format(IP_WHITE_CACHE_KEY , ip, merchId)
     */
    public static final String IP_WHITE_CACHE_KEY = "IPWhiteList:%s:%s";


}
