package com.sb.example.common;

/**
 * 常量对象
 * Created by lwnull2018@gmail.com ON 2021/3/4.
 */
public class RichConstants {

    /**
     * 控制层失败操作结果返回值
     */
    public static Integer FAIL = 0;

    /**
     * 控制层成功操作结果返回值
     */
    public static Integer SUCCESS = 1;


    /**
     * REDISSON 锁的持有时长
     */
    public final static int REDISSON_LEASETIME_SEC = 30;

    /**
     *  REDISSON 获取锁等待时长
     */
    public final static int REDISSON_WAITTIME_SEC = 20;


}
