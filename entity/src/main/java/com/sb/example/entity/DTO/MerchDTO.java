package com.sb.example.entity.DTO;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 用户(商户)表
 * Created by lwnull2018@gmail.com ON 2021/2/1.
 */
@Data
public class MerchDTO {

    private Integer id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 注册时间
     */
    private String createTime;

    /**
     * 最后登录时间
     */
    private String lastTime;

    /**
     * 最后登录ip
     */
    private String lastIp;

    /**
     * 手机号码
     */
    private String mobile;

    private String appkey;

    /**
     * 会员等级
     */
    private Integer level;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 上级代理id
     */
    private Integer agentId;

    /**
     * 手续费
     */
    private BigDecimal agentFee;

    /**
     * 手续费配置
     */
    private String feeConfig;

    /**
     * 余额
     */
    private BigDecimal balance;

    /**
     * 冻结余额
     */
    private BigDecimal freezeBalance;

    private String startTime;

    private String endTime;
}
